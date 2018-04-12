package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.UserFans;
import com.jiangzuoyoupin.req.UserFansRegReq;
import com.jiangzuoyoupin.service.SmsService;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 用户controller
 *
 * @author chenshangbo
 * @date 2018/4/9
 */
@Api("用户模块")
@RestController
@RequestMapping("mina/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;


    /**
     * 功能模块: 发送短信验证码到手机
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 22:01:21
     */
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码到手机")
    @ApiImplicitParam(name = "req", value = "粉丝注册对象", dataType = "UserFansRegReq")
    @PostMapping(value = "/registerFans")
    public WebResult registerFans(@RequestBody UserFansRegReq req) {
        String errMsg = smsService.checkVerifyCode(req.getMobileNo(), req.getVerifyCode());
        if(StringUtils.isNotEmpty(errMsg)){
            return WebResultUtil.returnErrMsgResult(errMsg);
        }
        UserFans fans = new UserFans();
        BeanUtils.copyProperties(req, fans);
        userService.registerFans(fans);
        return WebResultUtil.returnResult();
    }


}
