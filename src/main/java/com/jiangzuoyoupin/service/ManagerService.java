package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.Shop;
import com.jiangzuoyoupin.domain.ShopBill;
import com.jiangzuoyoupin.domain.ShopManager;
import com.jiangzuoyoupin.dto.ShopBillDto;
import com.jiangzuoyoupin.mapper.ShopBillMapper;
import com.jiangzuoyoupin.mapper.ShopManagerMapper;
import com.jiangzuoyoupin.mapper.ShopMapper;
import com.jiangzuoyoupin.mapper.WeChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private ShopMapper shopMapper;

    @Autowired
    private ShopManagerMapper shopManagerMapper;

    /**
     * 功能模块: 保存店铺信息
     *
     * @param shopInfo
     * @return int
     * @author chenshangbo
     * @date 2018-04-24 23:06:01
     */
    public int saveShopInfo(Shop shopInfo) {
        return shopMapper.updateByWeChatUserId(shopInfo);
    }

    /**
     * 功能模块: 新增账单
     *
     * @param bill
     * @return int
     * @author chenshangbo
     * @date 2018-05-05 23:30:46
     */
    public int saveBill(ShopBill bill) {
        Shop shop = shopMapper.selectByPrimaryKey(bill.getShopId());
        if (shop.getWechatUserId().equals(bill.getCreateWeChatUserId())) {
            bill.setStatus(1);
        } else {
            bill.setStatus(0);
        }
        bill.setSortStatus(0);// 待位
        int count = shopBillMapper.insert(bill);
        if (count > 0) {
            // 更新上一条账单状态和排序状态
            shopBillMapper.updateEarliestSortStatus(bill.getShopId());
        }
        return count;
    }

    /**
     * 功能模块: 查询当前店铺总账单
     *
     * @param shopId
     * @return com.jiangzuoyoupin.dto.ShopBillDto
     * @author chenshangbo
     * @date 2018-05-05 23:31:04
     */
    public ShopBillDto getSaleTotalAmount(Long shopId) {
        ShopBillDto dto = shopBillMapper.getSaleTotalAmount(shopId);
        List<ShopBillDto> freeList = shopBillMapper.selectFreeBillList(shopId);
        if(freeList != null && !freeList.isEmpty()){
            dto.setFreeCount(freeList.size());
        }else{
            dto.setFreeCount(0);
        }
        return dto;
    }

    /**
     * 功能模块: 查询当前店铺总账单
     *
     * @param shopId
     * @return com.jiangzuoyoupin.dto.ShopBillDto
     * @author chenshangbo
     * @date 2018-05-05 23:31:04
     */
    public List<ShopBillDto> selectBillList(Long shopId) {
        return shopBillMapper.selectBillList(shopId);
    }

    public int checkBill(Long id) {
        ShopBill param = new ShopBill();
        param.setId(id);
        param.setStatus(1);
        return shopBillMapper.updateByPrimaryKeySelective(param);
    }

    /**
     * 功能模块: 新增匠探
     *
     * @param manager
     * @return int
     * @author chenshangbo
     * @date 2018-05-06 15:27:51
     */
    public int addManager(ShopManager manager) {
        return shopManagerMapper.insert(manager);
    }

    /**
     * 功能模块: 店铺匠探列表
     *
     * @param shopId
     * @return java.util.List<com.jiangzuoyoupin.domain.ShopManager>
     * @author chenshangbo
     * @date 2018-05-06 15:47:28
     */
    public List<ShopManager> selectManagerList(Long shopId) {
        return shopManagerMapper.selectManagerList(shopId);
    }

    /**
     * 功能模块: 删除匠探
     *
     * @param id
     * @return int
     * @author chenshangbo
     * @date 2018-05-06 15:47:53
     */
    public int delManager(Long id) {
        return shopManagerMapper.deleteByPrimaryKey(id);
    }

    public Shop getShopInfo(Long shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }
}
