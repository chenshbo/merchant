package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.ShopScanRecord;
import com.jiangzuoyoupin.dto.ShopScanRecordDto;
import com.jiangzuoyoupin.req.ScanRecordQueryReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopScanRecordMapper {
    int insert(ShopScanRecord record);

    int insertSelective(ShopScanRecord record);

    List<ShopScanRecordDto> selectMyScanList(ScanRecordQueryReq queryReq);

    List<ShopScanRecord> selectByParams(ShopScanRecord record);

    int updateCollectStatus(ShopScanRecord scanRecord);

    ShopScanRecord getLatestRecord(@Param("weChatUserId") Long weChatUserId);
}