package com.jiangzuoyoupin.controller.mina;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.controller.common.BaseController;
import com.jiangzuoyoupin.domain.ShopScanRecord;
import com.jiangzuoyoupin.dto.ShopScanRecordDto;
import com.jiangzuoyoupin.req.CollectStatusUpdateReq;
import com.jiangzuoyoupin.req.IdReq;
import com.jiangzuoyoupin.req.ScanRecordQueryReq;
import com.jiangzuoyoupin.req.ShopIdAndWeChatUserIdReq;
import com.jiangzuoyoupin.service.CollectService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.ShopScanRecordListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能模块: 匠商收集模块
 *
 * @author chenshangbo
 * @date 2018-05-09 21:48:59
 */
@Auth
@Api("小程序-匠商收集模块")
@RestController
@RequestMapping("mina/collect")
public class CollectController extends BaseController {

    @Autowired
    private CollectService collectService;

    @ApiOperation(value = "新增扫描记录", notes = "用户扫描店铺二维码，新增扫描记录")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ShopIdAndWeChatUserIdReq")
    @PostMapping(value = "/addScanRecord")
    public WebResult addScanRecord(@RequestBody ShopIdAndWeChatUserIdReq req) {
        ShopScanRecord scanRecord = new ShopScanRecord();
        BeanUtils.copyProperties(req, scanRecord);
        int count = collectService.addScanRecord(scanRecord);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("新增扫描记录失败");
        }
        return WebResultUtil.returnResult();
    }

    @ApiOperation(value = "浏览记录", notes = "分页查询浏览/收藏记录")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "ScanRecordQueryReq")
    @PostMapping(value = "/selectMyScanList")
    public WebResult<List<ShopScanRecordListVO>> selectMyScanList(@RequestBody ScanRecordQueryReq req) {
        List<ShopScanRecordDto> billList = collectService.selectMyScanList(req);
        List<ShopScanRecordListVO> voList = billList.stream().map(res -> {
            ShopScanRecordListVO vo = new ShopScanRecordListVO();
            BeanUtils.copyProperties(res, vo);
            return vo;
        }).collect(Collectors.toList());
        return WebResultUtil.returnPageResult(voList, billList, req.getPagingRequired());
    }

    @ApiOperation(value = "增加收藏", notes = "收藏店铺")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "CollectStatusUpdateReq")
    @PostMapping(value = "/updateCollectStatus")
    public WebResult updateCollectStatus(@RequestBody CollectStatusUpdateReq req) {
        ShopScanRecord scanRecord = new ShopScanRecord();
        BeanUtils.copyProperties(req, scanRecord);
        int count = collectService.updateCollectStatus(scanRecord);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("收藏失败");
        }
        return WebResultUtil.returnResult();
    }

}
