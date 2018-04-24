package com.jiangzuoyoupin.controller.common;

import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能模块: 基础controller
 *
 * @author chenshangbo
 * @date 2018-04-21 00:17:16
 */
public class BaseController {

    @Autowired
    public UserService userService;

    public WeChatUser getWeChatUserByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Token");
        return userService.getUserInfoByToken(accessToken);
    }

}
