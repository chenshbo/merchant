package com.jiangzuoyoupin.controller.common;

import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.Province;
import com.jiangzuoyoupin.req.IdReq;
import com.jiangzuoyoupin.req.NameReq;
import com.jiangzuoyoupin.service.DictService;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.SelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典controller
 *
 * @author chenshangbo
 * @date 2018-4-8 22:09:04
 */
@Api("数据字典模块")
@RestController
@RequestMapping("common/dict")
public class DictController extends BaseController{

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "省份列表接口", notes = "条件查询省份列表")
    @ApiImplicitParam(name = "req", value = "name请求对象", dataType = "NameReq")
    @PostMapping(value = "/selectProvinceList")
    public WebResult<List<SelectVO>> selectProvinceList(@RequestBody NameReq req) {
        Province province = new Province();
        BeanUtils.copyProperties(req, province);
        List<Province> provinceList = dictService.selectProvinceList(province);
        return WebResultUtil.returnResult(poList2voList(provinceList, SelectVO.class));
    }




}
