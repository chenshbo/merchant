package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.Area;
import com.jiangzuoyoupin.domain.City;
import com.jiangzuoyoupin.domain.Province;
import com.jiangzuoyoupin.mapper.AreaMapper;
import com.jiangzuoyoupin.mapper.CityMapper;
import com.jiangzuoyoupin.mapper.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */
@Service("dictService")
public class DictService {

    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 功能描述: 查询省份列表
     *
     * @param province
     * @return: java.util.List<com.jiangzuoyoupin.domain.Province>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018/4/9 14:45
     */
    public List<Province> selectProvinceList(Province province) {
        return provinceMapper.selectListByParams(province);
    }


    /**
     * 功能描述: 根据参数查询城市列表
     *
     * @param city
     * @return: java.util.List<com.jiangzuoyoupin.domain.City>
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018/4/9 14:46
     */
    public List<City> selectCityList(City city) {
        return cityMapper.selectListByParams(city);
    }

    public List<Area> selectAreaList(Area area) {
        return areaMapper.selectListByParams(area);
    }
}
