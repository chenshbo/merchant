package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.WeChatPayOrder;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.mapper.ShopBillMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        ShopBill first = shopBillMapper.getFirst(shopId,weChatUserId);
        if (first == null || !first.getCustomWeChatUserId().equals(weChatUserId)) {
            return "100";
        }
        ShopBillDto saleTotalAmount = shopBillMapper.getSaleTotalAmount(shopId);
        if (saleTotalAmount == null || saleTotalAmount.getTotalReward().equals(0.00)) {
            return "0";
        }
        return String.format("%.2f", saleTotalAmount.getTotalReward() / first.getAmount() * 100).toString();
    }

    public List<ShopBillDto> selectMyBillList(Long shopId, Long weChatUserId) {
        List<ShopBillDto> billList = shopBillMapper.selectBillList(shopId);
        List<ShopBillDto> resList = new ArrayList<>();
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getCustomWeChatUserId().equals(weChatUserId)) {
                billList.get(i).setRank(i + 1);
                resList.add(billList.get(i));
            }
        }
        return resList;
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

    public int withdrawCash(WeChatPayOrder payOrder) {
        // 更新状态
        ShopBill param = new ShopBill();
        param.setId(payOrder.getShopBillId());
        param.setStatus(4); // 完成
        param.setSortStatus(2); // 完成
        int count = shopBillMapper.updateByPrimaryKeySelective(param);
        if (count > 0) {
            ShopBill shopBill = shopBillMapper.selectByPrimaryKey(payOrder.getShopBillId());
            // 扣除用户余额
            WeChatUser update = new WeChatUser();
            update.setId(payOrder.getWechatUserId());
            update.setBalance(-shopBill.getAmount());
            weChatUserMapper.updateBalance(update);
        }
        return count;
    }

    public ShopBillDto getBillInfoById(Long id) {
        return shopBillMapper.getBillInfoById(id);
    }

    public Double getWaitingTotalAmount(Long shopId, Long weChatUserId) {
        return shopBillMapper.getWaitingTotalAmount(shopId, weChatUserId);
    }

    public int getWaitingCount(Long shopId, Long weChatUserId) {
        List<ShopBill> waitingBillList = shopBillMapper.selectWaitingBillList(shopId);
        int count = 0;
        for (int i = 0; i < waitingBillList.size(); i++) {
            if (waitingBillList.get(i).getCustomWeChatUserId().equals(weChatUserId)) {
                count = i+1;
                break;
            }
        }
        List<ShopBill> collect = waitingBillList.stream()
                .filter(shopBill -> shopBill.getCustomWeChatUserId().equals(weChatUserId))
                .collect(Collectors.toList());
        if(!collect.isEmpty()){
            count = count - 1;
        }else{
            count = waitingBillList.size();
        }
        return count < 0 ? 0 : count;
    }
}
