package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.Shop;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.req.BillSaveReq;
import com.jiangzuoyoupin.req.ShopInfoSaveReq;
import com.jiangzuoyoupin.service.ManagerService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能模块: 运营中心controller
 *
 * @author chenshangbo
 * @date 2018-04-24 23:27:51
 */
@Auth
@Api("公众号-运营中心模块")
@RestController
@RequestMapping("mina/manager")
public class ManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;

    /**
     * 功能模块: 保存店铺信息
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:27:03
     */
    @ApiOperation(value = "保存店铺信息", notes = "保存店铺信息")
    @ApiImplicitParam(name = "req", value = "店铺保存对象", dataType = "ShopInfoSaveReq")
    @PostMapping(value = "/saveShopInfo")
    public WebResult saveShopInfo(@RequestBody ShopInfoSaveReq req,HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        Shop shopInfo = new Shop();
        BeanUtils.copyProperties(req, shopInfo);
        shopInfo.setWechatUserId(weChatUser.getId());
        int res = managerService.saveShopInfo(shopInfo);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("保存失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能描述: TODO
     *
     * @param
     * @return: com.jiangzuoyoupin.base.WebResult
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-27 10:51:21
     */
    @ApiOperation(value = "申请开通模块权限", notes = "申请开通模块权限")
    @PostMapping(value = "/module/apply")
    public WebResult moduleApply() {
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 账单保存
     *
     * @param req
     * @param request
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:02:51
     */
    @ApiOperation(value = "账单保存", notes = "保存粉丝消费账单")
    @ApiImplicitParam(name = "req", value = "消费账单对象", dataType = "BillSaveReq")
    @PostMapping(value = "/bill/save")
    public WebResult saveBill(@RequestBody BillSaveReq req, HttpServletRequest request) {
        WeChatUser weChatUser = getWeChatUserByToken(request);
        if (weChatUser == null) {
            return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
        }
        WeChatUser fans = userService.getWechatUserByMobileNo(req.getCustomMobileNo());
        if(fans == null){
            return WebResultUtil.returnErrMsgResult("该客户还未注册，请注册再试");
        }
        ShopBill shopBill = new ShopBill();
        BeanUtils.copyProperties(req,shopBill);
        shopBill.setCreateWechatUserId(weChatUser.getId());
        shopBill.setCustomWechatUserId(fans.getId());
        int count = managerService.saveBill(shopBill);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("新增失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "查询总销售额和奖金池信息", notes = "根据shopId查询总销售额和奖金池信息")
    @GetMapping(value = "/getSaleTotalBill/{shopId}")
    public WebResult getSaleTotalAmount(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "查询总销售额和奖金池信息", notes = "根据shopId查询总销售额和奖金池信息")
    @PostMapping(value = "/selectBillList/{shopId}")
    public WebResult selectBillList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        return WebResultUtil.returnResult();
    }

}
