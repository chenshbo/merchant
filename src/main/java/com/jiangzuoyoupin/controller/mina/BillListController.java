package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.req.ShopIdAndWeChatUserIdReq;
import com.jiangzuoyoupin.service.BillService;
import com.jiangzuoyoupin.utils.ConvertUtils;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.BillListVO;
import com.jiangzuoyoupin.vo.MyBillListVO;
import com.jiangzuoyoupin.vo.MyScheduleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if(weChatUser != null){
            vo.setNickName(weChatUser.getNickName());
        }
        String schedule = billService.getMySchedule(req.getShopId(),req.getWeChatUserId());
        vo.setSchedule(schedule);
        return WebResultUtil.returnResult(schedule);
    }

    @ApiOperation(value = "我的消费列表", notes = "该店铺我的消费列表,sortStatus=1/status=1显示申请，sortStatus=1/status=2显示待转账，sortStatus=1/status=3显示提现，sortStatus=2/status=4显示完成")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ShopIdAndWeChatUserIdReq")
    @PostMapping(value = "/selectMyBillList")
    public WebResult<List<MyBillListVO>> selectMyBillList(@RequestBody ShopIdAndWeChatUserIdReq req) {
        List<ShopBillDto> billList = billService.selectMyBillList(req.getShopId(),req.getWeChatUserId());
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList,MyBillListVO.class));
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
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("申请免单失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "提现", notes = "提现")
    @GetMapping(value = "/my/withdrawCash/{id}")
    public WebResult myWithdrawCash(@ApiParam(name = "id", value = "账单id", required = true) @PathVariable Long id) {
        int count = billService.withdrawCash(id);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("提现失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "当前排位榜列表", notes = "店铺当前排位榜列表")
    @GetMapping(value = "/current/{shopId}")
    public WebResult<List<BillListVO>> selectCurrentList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopBillDto> billList = billService.selectCurrentBillList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList,BillListVO.class));
    }

    @ApiOperation(value = "历史免单榜列表", notes = "店铺历史免单榜列表")
    @GetMapping(value = "/free/{shopId}")
    public WebResult selectFreeList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopBillDto> billList = billService.selectFreeBillList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(billList,BillListVO.class));
    }

}
