package com.jiangzuoyoupin.controller.officialAccounts;

import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.ShopInfo;
import com.jiangzuoyoupin.domain.UserShopowner;
import com.jiangzuoyoupin.req.ShopInfoSaveReq;
import com.jiangzuoyoupin.req.UserShopownerRegReq;
import com.jiangzuoyoupin.service.ManagerService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api("公众号-运营中心模块")
@RestController
@RequestMapping("officialAccounts/manager")
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
    public WebResult saveShopInfo(@RequestBody ShopInfoSaveReq req) {
        ShopInfo shopInfo = new ShopInfo();
        BeanUtils.copyProperties(req, shopInfo);
        int res = managerService.saveShopInfo(shopInfo);
        if (res == 0) {
            WebResultUtil.returnErrMsgResult("保存失败");
        }
        return WebResultUtil.returnResult();
    }

    /**
     * 功能模块: 店主注册申请
     *
     * @param req
     * @param request
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-24 23:02:51
     */
    @ApiOperation(value = "店主注册申请", notes = "店主注册申请")
    @ApiImplicitParam(name = "req", value = "店主注册对象", dataType = "UserShopownerRegReq")
    @PostMapping(value = "/saveBill")
    public WebResult saveBill(@RequestBody UserShopownerRegReq req, HttpServletRequest request) {
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "查询总销售额和奖金池信息", notes = "根据shopownerId查询总销售额和奖金池信息")
    @GetMapping(value = "/getSaleTotalAmount/{shopownerId}")
    public WebResult getSaleTotalAmount(@ApiParam(name = "shopownerId", value = "店主id", required = true) @PathVariable Long shopownerId) {
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "查询总销售额和奖金池信息", notes = "根据shopownerId查询总销售额和奖金池信息")
    @PostMapping(value = "/selectBillList/{shopownerId}")
    public WebResult selectBillList(@ApiParam(name = "shopownerId", value = "店主id", required = true) @PathVariable Long shopownerId) {
        return WebResultUtil.returnResult();
    }

}
