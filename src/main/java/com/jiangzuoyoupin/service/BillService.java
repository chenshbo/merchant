package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.mapper.ShopBillMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import com.jiangzuoyoupin.req.ShopIdAndWeChatUserIdReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (first == null || !first.getCustomWeChatUserId().equals(weChatUserId)) {
            return "0.00";
        }
        ShopBillDto saleTotalAmount = shopBillMapper.getSaleTotalAmount(shopId);
        if (saleTotalAmount == null || saleTotalAmount.getTotalReward().equals(0.00)) {
            return "0.00";
        }
        return String.format("%.2f", saleTotalAmount.getTotalReward() / first.getAmount() * 100).toString();
    }

    public List<ShopBillDto> selectMyBillList(Long shopId, Long weChatUserId) {
        List<ShopBillDto> billList = shopBillMapper.selectBillList(shopId);
        List<ShopBillDto> resList = new ArrayList<>();
        for(int i = 0;i < billList.size();i++){
            if(billList.get(i).getCustomWeChatUserId().equals(weChatUserId)){
                billList.get(i).setRank(i+1);
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
        // TODO 1平台支付成功
        // 更新状态
        ShopBill param = new ShopBill();
        param.setId(payOrder.getShopBillId());
        param.setStatus(4); // 完成
        param.setSortStatus(2); // 完成
        shopBillMapper.updateByPrimaryKeySelective(param);

        // 扣除用户余额
        WeChatUser update = new WeChatUser();
        update.setId(shopBill.getCustomWeChatUserId());
        update.setBalance(update.getBalance() - shopBill.getAmount());
        weChatUserMapper.updateByPrimaryKeySelective(update);

        return 1;
    }

    public ShopBillDto getBillInfoById(Long id) {
        return shopBillMapper.getBillInfoById(id);
    }

    public static void main(String[] args) {
        System.out.println(123412/100);
    }
}
