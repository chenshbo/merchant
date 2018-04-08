package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.domain.User;
import com.jiangzuoyoupin.mapper.TestUserMapper;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.vo.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/7.
 */
@RestController
@RequestMapping("mina/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TestUserMapper testUserMapper;

    @PostMapping("/save")
    public WebResult save(){
        return WebResult.success(testUserMapper.selectByPrimaryKey(1l));
    }


}
