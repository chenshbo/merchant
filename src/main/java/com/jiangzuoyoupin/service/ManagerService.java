package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.Shop;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.mapper.ShopMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
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
    private WeChatUserMapper weChatUserMapper;

    @Autowired
    private ShopBillMapper shopBillMapper;

    @Autowired
    private ShopMapper shopownerMapper;

    /**
     * 功能模块: 保存店铺信息
     *
     * @param shopInfo
     * @return int
     * @author chenshangbo
     * @date 2018-04-24 23:06:01
     */
    public int saveShopInfo(Shop shopInfo) {
        return shopownerMapper.updateByWeChatUserId(shopInfo);
    }

    public int saveBill(ShopBill bill) {
        return shopBillMapper.insert(bill);
    }
}
