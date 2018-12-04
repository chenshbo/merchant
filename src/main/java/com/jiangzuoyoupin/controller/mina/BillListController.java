package com.jiangzuoyoupin.controller.mina;

import com.alibaba.fastjson.JSONObject;
import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.WeChatPayOrder;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.req.ShopIdAndWeChatUserIdReq;
import com.jiangzuoyoupin.req.WithdrawCash2CardReq;
import com.jiangzuoyoupin.service.BillService;
import com.jiangzuoyoupin.utils.*;
import com.jiangzuoyoupin.vo.BillListVO;
import com.jiangzuoyoupin.vo.MyBillListVO;
import com.jiangzuoyoupin.vo.MyBillTotalVO;
import com.jiangzuoyoupin.vo.MyScheduleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能模块: 运营中心controller
 *
 * @author chenshangbo
 * @date 2018-04-24 23:27:51
 */
@Auth
@Api("小程序-榜单模块")
@RestController
@RequestMapping("mina/billList")
public class BillListController extends BaseController {

    @Autowired
    private BillService billService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 功能模块: 保存店铺信息
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:27:03
     */
    @ApiOperation(value = "我的进度", notes = "保存店铺信息")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ShopIdAndWeChatUserIdReq")
    @PostMapping(value = "/my/schedule")
    public WebResult<MyScheduleVO> mySchedule(@RequestBody ShopIdAndWeChatUserIdReq req, HttpServletRequest request) {
        MyScheduleVO vo = new MyScheduleVO();
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser != null) {
            vo.setNickName(weChatUser.getNickName());
        }
        String schedule = billService.getMySchedule(req.getShopId(), req.getWeChatUserId());
        vo.setSchedule(schedule);
        return WebResultUtil.returnResult(schedule);
    }

    @ApiOperation(value = "我的消费列表", notes = "该店铺我的消费列表,sortStatus=1/status=1显示申请，sortStatus=1/status=2显示待转账，sortStatus=1/status=3显示提现，sortStatus=2/status=4显示完成")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ShopIdAndWeChatUserIdReq")
    @PostMapping(value = "/selectMyBillList")
    public WebResult<List<MyBillListVO>> selectMyBillList(@RequestBody ShopIdAndWeChatUserIdReq req) {
        List<ShopBillDto> billList = billService.selectMyBillList(req.getShopId(), req.getWeChatUserId());
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList, MyBillListVO.class));
    }

    /**
     * 功能模块: 申请免单
     *
     * @param id
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-05-06 21:34:05
     */
    @ApiOperation(value = "申请免单", notes = "申请免单")
    @GetMapping(value = "/my/apply/{id}")
    public WebResult myApply(@ApiParam(name = "id", value = "账单id", required = true) @PathVariable Long id) {
        int count = billService.applyFree(id);
        if (count == 0) {
            return WebResultUtil.returnErrMsgResult("申请免单失败");
        }
        return WebResultUtil.returnResult();
    }

    @Deprecated
    @ApiOperation(value = "提现到微信零钱包", notes = "提现到微信零钱包")
    @GetMapping(value = "/my/withdrawCash2Wallet/{id}")
    public WebResult myWithdrawCash2Wallet(@ApiParam(name = "id", value = "账单id", required = true) @PathVariable Long id) {
        ShopBillDto shopBillDto = billService.getBillInfoById(id);
        System.out.println("BillListController.myWithdrawCash2Wallet-是否是19的账单-"+(shopBillDto.getCustomWeChatUserId().intValue() != 19));
        if(shopBillDto.getCustomWeChatUserId().intValue() != 19){
            return WebResultUtil.returnErrMsgResult("提现失败（余额不足）");
        }
        WeChatUser weChatUser = userService.getUserInfoById(shopBillDto.getCustomWeChatUserId());
        if(weChatUser != null && weChatUser.getBalance() < shopBillDto.getAmount()){
            return WebResultUtil.returnErrMsgResult("提现失败，匠子余额不足");
        }
        if(NumberUtil.checkAmount(shopBillDto.getAmount())){
            return WebResultUtil.returnErrMsgResult("提现失败，提现金额小于1.5");
        }
        String tradeNo = String.valueOf(idWorker.nextId());
        String openId = weChatUser.getOpenId();
//        Long totalFee = shopBillDto.getAmount().longValue() * 100;
//        double totalFee = 2.5;
        Map<String, String> reqData = new HashMap<>();
        reqData.put("desc", "免单提现");// 商品描述
//        String.valueOf(NumberUtil.getWeChatFenAmount(shopBillDto.getAmount()))
        reqData.put("amount", String.valueOf(NumberUtil.getWeChatFenAmount(shopBillDto.getAmount()))); // 金额
        reqData.put("openid", openId);
        reqData.put("partner_trade_no", tradeNo); // 商户订单号
        // 添加到缓存
        if(CacheMap.get(id.toString()) != null){
            return WebResultUtil.returnErrMsgResult("提现失败，交易正在处理中，请稍后再试");
        }
        CacheMap.put(id.toString(),reqData);
        Map<String, String> resMap = mchPay2Wallet(reqData);
        if (resMap == null) {
            CacheMap.del(id.toString());
            return WebResultUtil.returnErrMsgResult("提现失败");
        }
        String result_code = resMap.get("result_code");// 业务结果
        String return_code = resMap.get("return_code");// SUCCESS/FAIL
        if (!"SUCCESS".equals(return_code)) {
            System.err.println("微信返回的交易状态不正确（" + resMap.get("return_msg") + "," + resMap.get("err_code_des") + "）");
            CacheMap.del(id.toString());
            return WebResultUtil.returnErrMsgResult("提现失败");
        }
        if (!"SUCCESS".equals(result_code)) {
            System.err.println("微信返回的交易状态不正确（" + resMap.get("return_msg") + "," + resMap.get("err_code_des") + "）");
            CacheMap.del(id.toString());
            return WebResultUtil.returnErrMsgResult("提现失败（" + resMap.get("err_code_des") + "）");
        }
        System.out.println("withdrawCash2Wallet-res---" + JSONObject.toJSONString(resMap).toString());
        WeChatPayOrder payOrder = new WeChatPayOrder();
        payOrder.setShopBillId(id);
        payOrder.setTradeNo(tradeNo);
        payOrder.setWechatTradeNo(resMap.get("payment_no"));
        String paymentTime = resMap.get("payment_time");
        payOrder.setPayTimeEnd(StringUtils.isNotEmpty(paymentTime) ? DateUtil.parseYMDHMS(paymentTime) : new Date());
        payOrder.setWechatUserId(shopBillDto.getCustomWeChatUserId());
        payOrder.setOrderType(3);
        payOrder.setTotalFee(NumberUtil.getFenAmount(shopBillDto.getAmount()));
        int count = billService.withdrawCash(payOrder);
        if (count == 0) {
            CacheMap.del(id.toString());
            return WebResultUtil.returnErrMsgResult("提现失败，更新账户余额错误");
        }
        // 提现成功，删除缓存中的key
        CacheMap.del(id.toString());
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "提现到银行卡", notes = "提现到银行卡")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "WithdrawCash2CardReq")
    @PostMapping(value = "/my/withdrawCash2Card")
    public WebResult myWithdrawCash2Card(@RequestBody WithdrawCash2CardReq req) {
        ShopBillDto shopBillDto = billService.getBillInfoById(req.getShopBillId());
        WeChatUser weChatUser = userService.getUserInfoById(req.getWeChatUserId());
        if(weChatUser != null && weChatUser.getBalance() < shopBillDto.getAmount()){
            return WebResultUtil.returnErrMsgResult("提现失败，匠子余额不足");
        }
        if(NumberUtil.checkAmount(shopBillDto.getAmount())){
            return WebResultUtil.returnErrMsgResult("提现失败，提现金额小于1.5");
        }
        String tradeNo = String.valueOf(idWorker.nextId());
//        Long totalFee = shopBillDto.getAmount().longValue() * 98;
        Map<String, String> reqData = new HashMap<>();
        reqData.put("desc", "免单提现");// 付款说明
        reqData.put("amount", String.valueOf(NumberUtil.getWeChatFenAmount(shopBillDto.getAmount()))); // 付款金额
        reqData.put("partner_trade_no", tradeNo); // 商户订单号
        reqData.put("enc_bank_no", RSAUtils.encrypt(req.getEncBankNo()));
        reqData.put("enc_true_name", RSAUtils.encrypt(req.getEncTrueName()));
        reqData.put("bank_code", req.getBankCode());
        Map<String, String> resMap = mchPay2Card(reqData);
        if (resMap == null) {
            return WebResultUtil.returnErrMsgResult("提现失败，微信支付发生错误");
        }
        String result_code = resMap.get("result_code");// 业务结果
        String return_code = resMap.get("return_code");// SUCCESS/FAIL
        if (!"SUCCESS".equals(return_code)) {
            System.err.println("微信返回的交易状态不正确（" + resMap.get("return_msg") + "," + resMap.get("err_code_des") + "）");
            return WebResultUtil.returnErrMsgResult("提现失败");
        }
        if (!"SUCCESS".equals(result_code)) {
            System.err.println("微信返回的交易状态不正确（" + resMap.get("return_msg") + "," + resMap.get("err_code_des") + "）");
            return WebResultUtil.returnErrMsgResult("提现失败（" + resMap.get("err_code_des") + "）");
        }
        System.out.println("mchPay-res---" + JSONObject.toJSONString(resMap).toString());
        WeChatPayOrder payOrder = new WeChatPayOrder();
        payOrder.setShopBillId(req.getShopBillId());
        payOrder.setTradeNo(tradeNo);
        payOrder.setWechatTradeNo(resMap.get("payment_no"));
        String paymentTime = resMap.get("payment_time");
        int cmmsAmt = Integer.parseInt(resMap.get("cmms_amt"));
        System.out.println("手续费金额:" + cmmsAmt);
        payOrder.setPayTimeEnd(StringUtils.isNotEmpty(paymentTime) ? DateUtil.parseYMDHMS(paymentTime) : new Date());
        payOrder.setWechatUserId(shopBillDto.getCustomWeChatUserId());
        payOrder.setOrderType(3);
        payOrder.setTotalFee(NumberUtil.getFenAmount(shopBillDto.getAmount()));
        int count = billService.withdrawCash(payOrder);
        if (count == 0) {
            return WebResultUtil.returnErrMsgResult("提现失败，更新账户余额错误");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "当前排位榜列表", notes = "店铺当前排位榜列表")
    @GetMapping(value = "/current/{shopId}")
    public WebResult<List<BillListVO>> selectCurrentList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopBillDto> billList = billService.selectCurrentBillList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList, BillListVO.class));
    }

    @ApiOperation(value = "历史免单榜列表", notes = "店铺历史免单榜列表")
    @GetMapping(value = "/free/{shopId}")
    public WebResult selectFreeList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopBillDto> billList = billService.selectFreeBillList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList, BillListVO.class));
    }

    @ApiOperation(value = "我的消费总额", notes = "查询我的消费总额")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ShopIdAndWeChatUserIdReq")
    @PostMapping(value = "/my/totalAmount")
    public WebResult<MyBillTotalVO> myTotalAmount(@RequestBody ShopIdAndWeChatUserIdReq req) {
        MyBillTotalVO vo = new MyBillTotalVO();
        vo.setTotalAmount(billService.getWaitingTotalAmount(req.getShopId(), req.getWeChatUserId()));
        vo.setFreeCount(billService.getWaitingCount(req.getShopId(), req.getWeChatUserId()));
        return WebResultUtil.returnResult(vo);
    }

    public static void main(String[] args) {
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setId(433l);
        if(!"19".equals(weChatUser.getId().toString())){
            System.out.println(true);
        }
    }

}
