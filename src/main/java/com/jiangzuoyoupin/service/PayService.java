package com.jiangzuoyoupin.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.jiangzuoyoupin.domain.*;
import com.jiangzuoyoupin.dto.ShopScanRecordDto;
import com.jiangzuoyoupin.mapper.*;
import com.jiangzuoyoupin.req.ScanRecordQueryReq;
import com.jiangzuoyoupin.utils.DateUtil;
import com.jiangzuoyoupin.utils.NumberUtil;
import com.jiangzuoyoupin.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 功能模块: 支付service
 *
 * @author chenshangbo
 * @date 2018-05-09 22:51:43
 */
@Service
public class PayService {

    @Autowired
    private WeChatUserMapper weChatUserMapper;
    @Autowired
    private WeChatPayOrderMapper payOrderMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ShopBillMapper shopBillMapper;

    public int updatePayOrder(Map<String,String> resMap) {
        String time_end = resMap.get("time_end");//支付完成时间
        WeChatPayOrder update = new WeChatPayOrder();
        update.setTradeNo(resMap.get("out_trade_no"));// 获取商户订单号
        update.setWechatTradeNo(resMap.get("transaction_id"));//微信支付订单号
        try {
            update.setPayTimeEnd(DateUtil.parseDate(time_end,"yyyyMMddHHmmss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int count = payOrderMapper.updateByTradeNo(update);
        String attach = resMap.get("attach");//商家数据包
        JSONObject jsonObject = JSONObject.parseObject(attach);
        // 功能开通
        if(count > 0 ) {
            if (jsonObject.get("orderType").equals(1)) {
                String openid = resMap.get("openid");//用户标识
                WeChatUser param = new WeChatUser();
                param.setOpenId(openid);
                WeChatUser user = weChatUserMapper.getByParams(param);
                Shop shop = shopMapper.selectByWeChatUserId(user.getId());
                if (shop != null) {
                    Shop updateShop = new Shop();
                    updateShop.setId(shop.getId());
                    updateShop.setIsOpenPermissions(1);
                    shopMapper.updateByPrimaryKeySelective(updateShop);
                }
            }else if(jsonObject.get("orderType").equals(2)){
                ShopBill param = new ShopBill();
                param.setId((Long)jsonObject.get("shopBillId"));
                param.setStatus(3); // 提现
                param.setSortStatus(2); // 完成
                shopBillMapper.updateByPrimaryKeySelective(param);

                // 增加用户余额
                WeChatUser userUpdate = new WeChatUser();
                userUpdate.setId((Long)jsonObject.get("customWeChatUserId"));
                userUpdate.setBalance(NumberUtil.getDoubleAmount(resMap.get("total_fee")));
                weChatUserMapper.updateBalance(userUpdate);
            }
        }
        return count;
    }

    public int addPayOrder(WeChatPayOrder payOrder) {
        return payOrderMapper.insert(payOrder);
    }

    public List<WeChatPayOrder> selectWeChatOrderList(WeChatPayOrder param) {
        return payOrderMapper.selectWeChatOrderList(param);
    }
}
