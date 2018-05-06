package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.mapper.ShopBillMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import com.jiangzuoyoupin.req.ShopIdAndWeChatUserIdReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能模块: 账单service
 *
 * @author chenshangbo
 * @date 2018-04-24 23:25:12
 */
@Service
public class BillService {

    @Autowired
    private ShopBillMapper shopBillMapper;

    @Autowired
    private WeChatUserMapper weChatUserMapper;

    public String getMySchedule(Long shopId, Long weChatUserId) {
        ShopBill first = shopBillMapper.getFirst(shopId);
        if (first == null || !first.getCustomWechatUserId().equals(weChatUserId)) {
            return "0.00";
        }
        ShopBillDto saleTotalAmount = shopBillMapper.getSaleTotalAmount(shopId);
        if (saleTotalAmount == null || saleTotalAmount.getTotalReward().equals(0.00)) {
            return "0.00";
        }
        return String.format("%.2f", saleTotalAmount.getTotalReward() / first.getAmount()).toString();
    }

    public List<ShopBillDto> selectMyBillList(Long shopId, Long weChatUserId) {
        return shopBillMapper.selectMyBillList(shopId, weChatUserId);
    }

    public int applyFree(Long id) {
        ShopBill param = new ShopBill();
        param.setId(id);
        param.setStatus(2);
        return shopBillMapper.updateByPrimaryKeySelective(param);
    }

    public List<ShopBillDto> selectCurrentBillList(Long shopId) {
        return shopBillMapper.selectCurrBillList(shopId);
    }

    public List<ShopBillDto> selectFreeBillList(Long shopId) {
        return shopBillMapper.selectFreeBillList(shopId);
    }

    public int withdrawCash(Long id) {
        ShopBill shopBill = shopBillMapper.selectByPrimaryKey(id);
        // TODO 1平台支付成功
        // 更新状态
        ShopBill param = new ShopBill();
        param.setId(id);
        param.setStatus(4); // 完成
        param.setSortStatus(2); // 完成
        shopBillMapper.updateByPrimaryKeySelective(param);

        // 扣除用户余额
        WeChatUser update = new WeChatUser();
        update.setId(shopBill.getCustomWechatUserId());
        update.setBalance(update.getBalance() - shopBill.getAmount());
        weChatUserMapper.updateByPrimaryKeySelective(update);

        return 1;
    }

    public int transfer(Long id) {
        ShopBill shopBill = shopBillMapper.selectByPrimaryKey(id);
        // TODO 1店铺转账给平台 shopBill.getShopId()
        // 2更新状态
        ShopBill param = new ShopBill();
        param.setId(id);
        param.setStatus(3); // 提现
        param.setSortStatus(2); // 完成
        shopBillMapper.updateByPrimaryKeySelective(param);

        // 增加用户余额
        WeChatUser update = new WeChatUser();
        update.setId(shopBill.getCustomWechatUserId());
        update.setBalance(update.getBalance() + shopBill.getAmount());
        weChatUserMapper.updateByPrimaryKeySelective(update);

        return 1;
    }
}
