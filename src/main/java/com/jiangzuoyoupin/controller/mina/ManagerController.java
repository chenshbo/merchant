package com.jiangzuoyoupin.controller.mina;

import com.alibaba.fastjson.JSONObject;
import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.Shop;
import com.jiangzuoyoupin.domain.ShopManager;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.req.*;
import com.jiangzuoyoupin.service.BillService;
import com.jiangzuoyoupin.service.ManagerService;
import com.jiangzuoyoupin.utils.ConvertUtils;
import com.jiangzuoyoupin.utils.FileUtil;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.ShopBillListVO;
import com.jiangzuoyoupin.vo.ShopBillTotalVO;
import com.jiangzuoyoupin.vo.ShopManagerListVO;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 功能模块: 运营中心controller
 *
 * @author chenshangbo
 * @date 2018-04-24 23:27:51
 */
@Auth
@Api("小程序-运营中心模块")
@RestController
@RequestMapping("mina/manager")
public class ManagerController extends BaseController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private BillService billService;

    @Value("${root.url}")
    private String rootUrl;

    @Value("${wx.appid}")
    private String wxAppId;

    @Value("${wx.secret}")
    private String wxAppSecret;

    @ApiOperation(value = "店铺信息-保存", notes = "保存店铺信息")
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

    @ApiOperation(value = "店铺信息-查询", notes = "查询店铺信息")
    @GetMapping(value = "/getShopInfo/{shopId}")
    public WebResult<Shop> getShopInfo(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        Shop shop = managerService.getShopInfo(shopId);
        if (shop == null) {
            WebResultUtil.returnErrMsgResult("该店铺不存在");
        }
        if(StringUtils.isNotEmpty(shop.getBusinessLicenseImage())){
            shop.setBusinessLicenseImage(rootUrl + shop.getBusinessLicenseImage());
        }
        if(StringUtils.isNotEmpty(shop.getQrCodeImg())){
            shop.setQrCodeImg(rootUrl + shop.getQrCodeImg());
        }
        return WebResultUtil.returnResult(shop);
    }

    @ApiOperation(value = "功能开通-申请开通模块权限", notes = "申请开通模块权限")
    @ApiImplicitParam(name = "req", value = "申请开通功能请求对象", dataType = "ModuleApplyReq")
    @PostMapping(value = "/applyOpenPermissions")
    public WebResult applyOpenPermissions(@RequestBody ModuleApplyReq req) {
        if(req.getApplyType().intValue() == 2){
            if(!managerService.checkInvitationCode(req.getInvitationCode())){
                return WebResultUtil.returnErrMsgResult("邀请码已失效");
            }
        }
        int count = managerService.applyOpenPermissions(req.getShopId(),req.getInvitationCode());
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("开通失败");
        }
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
    @ApiOperation(value = "幸福账单-账单保存", notes = "保存粉丝消费账单")
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
        shopBill.setCreateWeChatUserId(weChatUser.getId());
        shopBill.setCustomWeChatUserId(fans.getId());

        int count = managerService.saveBill(shopBill);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("保存幸福账单失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "幸福账单-查询总销售额和奖金池信息", notes = "根据shopId查询总销售额和奖金池信息")
    @GetMapping(value = "/getSaleTotalBill/{shopId}")
    public WebResult<ShopBillTotalVO> getSaleTotalAmount(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        ShopBillDto result = managerService.getSaleTotalAmount(shopId);
        return WebResultUtil.returnResult(ConvertUtils.convert2vo(result,ShopBillTotalVO.class));
    }

    @ApiOperation(value = "幸福账单-查询账单列表", notes = "根据shopId查询总销售额和奖金池信息")
    @GetMapping(value = "/selectBillList/{shopId}")
    public WebResult<List<ShopBillListVO>> selectBillList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopBillDto> list = managerService.selectBillList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(list,ShopBillListVO.class));
    }

    @ApiOperation(value = "幸福账单-审核账单", notes = "审核账单")
    @GetMapping(value = "/checkBill/{id}")
    public WebResult checkBill(@ApiParam(name = "id", value = "账单id", required = true) @PathVariable Long id) {
        int count = managerService.checkBill(id);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("审核账单失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "幸福账单-转账", notes = "转账")
    @GetMapping(value = "/transfer/{id}")
    public WebResult transfer(@ApiParam(name = "id", value = "账单id", required = true) @PathVariable Long id) {
        int count = billService.transfer(id);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("转账失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "匠探团队-邀请", notes = "邀请匠探")
    @ApiImplicitParam(name = "req", value = "邀请对象", dataType = "ManagerAddReq")
    @PostMapping(value = "/addManager")
    public WebResult addManager(@RequestBody ManagerAddReq req) {
        WeChatUser fans = userService.getWechatUserByMobileNo(req.getMobileNo());
        if(fans == null){
            return WebResultUtil.returnErrMsgResult("该客户还未注册，请注册再试");
        }
        // 判断是否被注册
        if(managerService.checkManagerExist(fans.getId())){
            return WebResultUtil.returnErrMsgResult("该客户已注册店主或者已被其他店主注册");
        }
        ShopManager manager = new ShopManager();
        manager.setShopId(req.getShopId());
        manager.setWechatUserId(fans.getId());
        int count = managerService.addManager(manager);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("邀请匠探失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "匠探团队-列表", notes = "店铺匠探列表")
    @GetMapping(value = "/selectManagerList/{shopId}")
    public WebResult<ShopManagerListVO> selectManagerList(@ApiParam(name = "shopId", value = "店铺id", required = true) @PathVariable Long shopId) {
        List<ShopManager> managerList = managerService.selectManagerList(shopId);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(managerList,ShopManagerListVO.class));
    }

    @ApiOperation(value = "匠探团队-删除", notes = "删除店铺匠探")
    @ApiImplicitParam(name = "req", value = "id请求对象", dataType = "IdReq")
    @PostMapping(value = "/delManager")
    public WebResult delManager(@RequestBody IdReq req) {
        int count = managerService.delManager(req.getId());
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("删除匠探失败");
        }
        return WebResultUtil.returnResult();
    }

}
