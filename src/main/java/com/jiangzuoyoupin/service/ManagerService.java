package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.ShopInfo;
import com.jiangzuoyoupin.domain.UserShopowner;
import com.jiangzuoyoupin.mapper.ShopInfoMapper;
import com.jiangzuoyoupin.mapper.UserShopownerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能模块: 管理service
 *
 * @author chenshangbo
 * @date 2018-04-24 23:25:12
 */
@Service
public class ManagerService {

    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Autowired
    private UserShopownerMapper shopownerMapper;

    /**
     * 功能模块: 保存店铺信息
     *
     * @param shopInfo
     * @return int
     * @author chenshangbo
     * @date 2018-04-24 23:06:01
     */
    public int saveShopInfo(UserShopowner shopInfo) {
        return shopownerMapper.updateByWeChatUserId(shopInfo);
    }
}
