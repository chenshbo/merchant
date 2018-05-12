package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.req.*;
import com.jiangzuoyoupin.service.PayService;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.utils.ConvertUtils;
import com.jiangzuoyoupin.utils.DateUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.SupplierVO;
import com.jiangzuoyoupin.vo.WeChatOrderListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 用户controller
 *
 * @author chenshangbo
 * @date 2018/4/9
 */
@Auth
@Api("小程序-用户模块")
@RestController
@RequestMapping("mina/user")
public class UserController extends BaseController {

    @Value("${images.path}")
    private String imagesPath;

    @Value("${root.url}")
    private String rootUrl;

    @Autowired
    private SmsService smsService;

    @Autowired
    private PayService payService;

    /**
     * 功能模块: 粉丝注册
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "粉丝注册", notes = "粉丝注册")
    @ApiImplicitParam(name = "req", value = "粉丝注册对象", dataType = "FansRegReq")
    @PostMapping(value = "/fans/register")
    public WebResult registerFans(@RequestBody FansRegReq req) {
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        Fans fans = new Fans();
        BeanUtils.copyProperties(req, fans);
        int res = userService.registerFans(fans);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 供应商注册
     *
     * @param req
     * @param request
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-18 22:39:39
     */
    @Deprecated
    @ApiOperation(value = "供应商注册", notes = "供应商注册")
    @ApiImplicitParam(name = "req", value = "供应商注册对象", dataType = "SupplierRegReq")
    @PostMapping(value = "/supplier/register")
    public WebResult registerSupplier(@RequestBody SupplierRegReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(req, supplier);
        supplier.setStatus(0);
        supplier.setBusinessLicenseImage(imagesPath + "/" + supplier.getBusinessLicenseImage());
        supplier.setWechatUserId(weChatUser.getId());
        int res = userService.registerSupplier(supplier);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 店主注册申请
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:02:51
     */
    @ApiOperation(value = "店主注册申请", notes = "店主注册申请，成功返回shopId，需要更新用户角色为1和shopId")
    @ApiImplicitParam(name = "req", value = "店主注册对象", dataType = "ShopRegReq")
    @PostMapping(value = "/shop/register")
    public WebResult<Long> registerShop(@RequestBody ShopRegReq req) {
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if (StringUtils.isNotEmpty(errMsg)) {
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        Shop shop = new Shop();
        BeanUtils.copyProperties(req, shop);
        shop.setStatus(1);
        shop.setBusinessLicenseImage(imagesPath + "/" + shop.getBusinessLicenseImage());
        Long res = userService.registerShop(shop);
        if (res == null || !res.equals(0l)) {
            WebResultUtil.returnErrMsgResult("注册失败");
        }
        return WebResultUtil.returnResult(res);
    }

    /**
     * 功能模块: 发送短信验证码到手机
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码到手机")
    @ApiImplicitParam(name = "req", value = "发送短信请求对象", dataType = "SendVerifyCodeReq")
    @PostMapping(value = "/sendVerifyCode")
    public WebResult sendVerifyCode(@RequestBody SendVerifyCodeReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        if (StringUtils.isNotEmpty(weChatUser.getMobileNo())
                && !weChatUser.getMobileNo().equals(req.getMobileNo())) {
            return WebResultUtil.returnErrMsgResult("手机号码与微信注册时不一致，请重新输入");
        }
        if (!smsService.checkSendVerifyCodeCount(req.getMobileNo())) {
            return WebResultUtil.returnErrMsgResult("今日发送短信验证码次数已到达上限，请明天再试");
        }
        int res = smsService.sendVerifyCode(req);
        if(res == 0){
            return WebResultUtil.returnErrMsgResult("发送验证码失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "匠子余额", notes = "根据用户ID查询匠子余额")
    @GetMapping(value = "/getTotalBalance/{weChatUserId}")
    public WebResult getTotalBalance(@ApiParam(name = "weChatUserId", value = "用户id", required = true) @PathVariable Long weChatUserId) {
        WeChatUser weChatUser = userService.getUserInfoById(weChatUserId);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("用户不存在");
        }
        return WebResultUtil.returnResult(weChatUser.getBalance());
    }

    @ApiOperation(value = "微信账单列表", notes = "根据用户ID查询收入支出列表")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "WeChatOrderQueryReq")
    @GetMapping(value = "/selectWeChatOrderList")
    public WebResult<List<WeChatOrderListVO>> selectWeChatOrderList(@RequestBody WeChatOrderQueryReq req) {
        WeChatPayOrder param = new WeChatPayOrder();
        param.setWechatUserId(req.getWeChatUserId());
        param.setOrderType(req.getType());
        List<WeChatPayOrder> orderList = payService.selectWeChatOrderList(param);
        List<WeChatOrderListVO> voList = orderList.stream().map(res -> {
            WeChatOrderListVO vo = new WeChatOrderListVO();
            vo.setTotalFee(NumberUtil.getDoubleAmount(res.getTotalFee().toString()));
            vo.setPayTimeEnd(DateUtil.formatYMD(res.getPayTimeEnd()));
            return vo;
        }).collect(Collectors.toList());
        return WebResultUtil.returnResult(voList);
    }

}
