package com.jiangzuoyoupin.controller.back;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.req.AdminLoginReq;
import com.jiangzuoyoupin.req.SupplierQueryReq;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 后台用户审核controller
 *
 * @author: chenshangbo
 * @date: 2018-04-16 11:32:52
 */
@Auth
@Api("后台-认证模块")
@RestController
@RequestMapping("back/audit")
public class AuditController {

    @Autowired
    private UserService userService;

    /**
     * 功能描述: 条件查询供应商列表
     *
     * @param req
     * @return: com.jiangzuoyoupin.base.WebResult
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-17 21:07:13
     */
    @ApiOperation(value = "供应商列表", notes = "条件查询供应商列表")
    @ApiImplicitParam(name = "req", value = "条件查询对象", dataType = "SupplierQueryReq")
    @PostMapping(value = "/supplier/list")
    public WebResult selectSupplierList(@RequestBody SupplierQueryReq req) {
        if(!"admin".equals(req.getLoginName())
                || !"admin".equals(req.getLoginPwd())){
            return WebResultUtil.returnErrMsgResult("登录失败");
        }
        return WebResultUtil.returnResult();
    }

}
