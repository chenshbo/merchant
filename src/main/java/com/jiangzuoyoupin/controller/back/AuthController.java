package com.jiangzuoyoupin.controller.back;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.req.AdminLoginReq;
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
 * 功能描述: 后台用户controller
 *
 * @author: chenshangbo
 * @date: 2018-04-16 11:32:52
 */
@Auth
@Api("后台-认证模块")
@RestController
@RequestMapping("back/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "登录接口", notes = "管理员登录，默认账号admin/admin")
    @ApiImplicitParam(name = "req", value = "登录对象", dataType = "AdminLoginReq")
    @PostMapping(value = "/login")
    public WebResult login(@RequestBody AdminLoginReq req) {
        if(!"admin".equals(req.getLoginName())
                || !"admin".equals(req.getLoginPwd())){
            return WebResultUtil.returnErrMsgResult("登录失败");
        }
        return WebResultUtil.returnResult();
    }

}
