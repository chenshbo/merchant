package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.utils.MD5;
import com.jiangzuoyoupin.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by xutiantian on 2017/4/20.
 */
@Service
public class AuthService{
    @Autowired
    private JedisReadUtils jedisReadUtils;
    @Autowired
    private JedisWriteUtils jedisWriteUtils;

    public String login(String userName, String password) {
        String realPasswordMD5 = jedisReadUtils.hget(CacheKeyUtils.KEY_AUTH_LOGIN, userName);

        if (realPasswordMD5 != null && realPasswordMD5.equals(password)) {
            return generateAccessToken(userName, password);
        }

        return null;
    }

    public void logout(String userName) {
        String key = MessageFormat.format("{0}:{1}:{2}", CacheKeyUtils.KEY_AUTH_LOGIN, "token", userName);
        jedisWriteUtils.del(key);
    }

    @Override
    public boolean checkAccessToken(String userName, String accessToken) {
        String key = MessageFormat.format("{0}:{1}:{2}", CacheKeyUtils.KEY_AUTH_LOGIN, "token", userName);
        String realAccessToken = jedisReadUtils.get(key);
        if (accessToken == null || realAccessToken == null || !realAccessToken.equals(accessToken)) {
            return false;
        }

        jedisWriteUtils.save(key, realAccessToken, 3600);
        return true;
    }

    public String generateAccessToken(String userName, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append("account=");
        builder.append(userName);
        builder.append("&password=");
        builder.append(password);
        builder.append("&random=");
        builder.append(RandomUtils.getRandom(64));
        String accessToken = MD5.toMD5(builder.toString(), "utf8");
        String key = MessageFormat.format("{0}:{1}:{2}", CacheKeyUtils.KEY_AUTH_LOGIN, "token", userName);
        jedisWriteUtils.save(key, accessToken, 3600);
        return accessToken;
    }


    public static void main(String[] args) {
        System.out.println(MD5.toMD5("123456", "utf8"));
    }
}
