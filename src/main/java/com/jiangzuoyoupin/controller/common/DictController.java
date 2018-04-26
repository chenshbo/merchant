package com.jiangzuoyoupin.controller.common;

import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.Area;
import com.jiangzuoyoupin.domain.City;
import com.jiangzuoyoupin.domain.Module;
import com.jiangzuoyoupin.domain.Province;
import com.jiangzuoyoupin.req.NameReq;
import com.jiangzuoyoupin.service.DictService;
import com.jiangzuoyoupin.utils.ConvertUtils;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.AreaTreeVO;
import com.jiangzuoyoupin.vo.SelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典controller
 *
 * @author chenshangbo
 * @date 2018-4-8 22:09:04
 */
@Api("公共-数据字典模块")
@RestController
@RequestMapping("common/dict")
public class DictController{

    @Autowired
    private DictService dictService;

    /**
     * 功能描述: 身份列表接口
     *
     * @param req
     * @return: WebResult<List<SelectVO>>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018/4/9 14:33
     */
    @ApiOperation(value = "省份列表接口", notes = "条件查询省份列表")
    @ApiImplicitParam(name = "req", value = "name请求对象", dataType = "NameReq")
    @PostMapping(value = "/selectProvinceList")
    public WebResult<List<SelectVO>> selectProvinceList(@RequestBody NameReq req) {
        Province province = new Province();
        BeanUtils.copyProperties(req, province);
        List<Province> provinceList = dictService.selectProvinceList(province);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(provinceList, SelectVO.class));
    }


    /**
     * 功能描述: 城市列表接口
     *
     * @param provinceId
     * @return: com.jiangzuoyoupin.base.WebResult<java.util.List<com.jiangzuoyoupin.vo.SelectVO>>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018/4/9 14:53
     */
    @ApiOperation(value = "城市列表接口", notes = "根据provinceId查询城市列表")
    @GetMapping(value = "/selectCityList/{provinceId}")
    public WebResult<List<SelectVO>> selectCityList(@ApiParam(name = "provinceId", value = "省份id", required = true) @PathVariable Long provinceId) {
        City city = new City();
        city.setProvinceId(provinceId);
        List<City> cityList = dictService.selectCityList(city);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(cityList, SelectVO.class));
    }

    /**
     * 功能描述: 区县列表接口
     *
     * @param cityId
     * @return: com.jiangzuoyoupin.base.WebResult<java.util.List<com.jiangzuoyoupin.vo.SelectVO>>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-10 19-49-45
     */
    @ApiOperation(value = "区县列表接口", notes = "根据cityId查询区县列表")
    @GetMapping(value = "/selectAreaList/{cityId}")
    public WebResult<List<SelectVO>> selectAreaList(@ApiParam(name = "cityId", value = "城市id", required = true) @PathVariable Long cityId) {
        Area area = new Area();
        area.setCityId(cityId);
        List<Area> areaList = dictService.selectAreaList(area);
        return WebResultUtil.returnResult(ConvertUtils.poList2voList(areaList, SelectVO.class));
    }

    /**
     * 功能模块: 省市区树接口
     *
     * @param
     * @return com.jiangzuoyoupin.base.WebResult<java.util.List<com.jiangzuoyoupin.vo.AreaTreeVO>>
     * @author chenshangbo
     * @date 2018-04-10 22:43:44
     */
    @ApiOperation(value = "省市区树接口", notes = "省市区树接口")
    @GetMapping(value = "/selectAreaTree")
    public WebResult<List<AreaTreeVO>> selectAreaTree() {
        return WebResultUtil.returnResult(dictService.selectAreaTree());
    }

    @ApiOperation(value = "店主注册申请", notes = "店主注册申请")
    @PostMapping(value = "/module/select")
    public WebResult<List<Module>> selectModuleList() {
        return WebResultUtil.returnResult(dictService.selectModuleList());
    }

}
