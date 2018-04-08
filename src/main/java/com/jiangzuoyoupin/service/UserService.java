package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.User;
import com.jiangzuoyoupin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/4/7.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int saveUser(User user){
        userMapper.selectByPrimaryKey(1l);
        return userMapper.insert(user);
    }

}
