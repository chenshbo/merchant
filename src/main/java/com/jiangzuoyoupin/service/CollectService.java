package com.jiangzuoyoupin.service;

import com.github.pagehelper.PageHelper;
import com.jiangzuoyoupin.domain.ShopScanRecord;
import com.jiangzuoyoupin.dto.ShopScanRecordDto;
import com.jiangzuoyoupin.mapper.ShopScanRecordMapper;
import com.jiangzuoyoupin.req.ScanRecordQueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 功能模块: 收藏service
 *
 * @author chenshangbo
 * @date 2018-05-09 22:51:43
 */
@Service
public class CollectService {

    @Autowired
    private ShopScanRecordMapper scanRecordMapper;

    public int addScanRecord(ShopScanRecord scanRecord) {
        List<ShopScanRecord> scanRecordList = scanRecordMapper.selectByParams(scanRecord);
        if(CollectionUtils.isEmpty(scanRecordList)){
            scanRecord.setCollectStatus(0);
            return scanRecordMapper.insert(scanRecord);
        }
        return scanRecordMapper.updateCollectStatus(scanRecordList.get(0));
    }

    public List<ShopScanRecordDto> selectMyScanList(ScanRecordQueryReq queryReq) {
        if(queryReq.getPagingRequired()){
            PageHelper.startPage(queryReq.getCurrPage(),queryReq.getPageRows());
        }
        return scanRecordMapper.selectMyScanList(queryReq);
    }

    public int updateCollectStatus(ShopScanRecord scanRecord) {
        scanRecord.setCollectStatus(scanRecord.getCollectStatus());
        return scanRecordMapper.updateCollectStatus(scanRecord);
    }
}
