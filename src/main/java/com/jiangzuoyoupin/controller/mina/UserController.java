package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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




}
