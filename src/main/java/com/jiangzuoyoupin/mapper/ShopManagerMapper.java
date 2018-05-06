package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.ShopManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopManager record);

    int insertSelective(ShopManager record);

    ShopManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopManager record);

    int updateByPrimaryKey(ShopManager record);

    ShopManager selectByWeChatUserId(Long weChatUserId);

    List<ShopManager> selectManagerList(@Param("shopId") Long shopId);
}