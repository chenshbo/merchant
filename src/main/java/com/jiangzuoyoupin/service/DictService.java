package com.jiangzuoyoupin.service;

import com.jiangzuoyoupin.domain.Area;
import com.jiangzuoyoupin.domain.City;
import com.jiangzuoyoupin.domain.Province;
import com.jiangzuoyoupin.mapper.AreaMapper;
import com.jiangzuoyoupin.mapper.CityMapper;
import com.jiangzuoyoupin.mapper.ProvinceMapper;
import com.jiangzuoyoupin.vo.AreaTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 功能模块: 根据参数查询区县列表
     *
     * @param area
     * @return java.util.List<com.jiangzuoyoupin.domain.Area>
     * @author chenshangbo
     * @date 2018-04-10 22:06:26
     */
    public List<Area> selectAreaList(Area area) {
        return areaMapper.selectListByParams(area);
    }

    /**
     * 功能模块: 构建区域树
     *
     * @param
     * @return java.util.List<com.jiangzuoyoupin.vo.AreaTreeVO>
     * @author chenshangbo
     * @date 2018-04-10 22:57:40
     */
    public List<AreaTreeVO> selectAreaTree() {
        List<AreaTreeVO> provinceList = new ArrayList<>();
        List<AreaTreeVO> cityList = new ArrayList<>();
        List<AreaTreeVO> areaList = new ArrayList<>();

        areaMapper.selectListByParams(new Area()).forEach(area -> {
            AreaTreeVO vo = new AreaTreeVO();
            BeanUtils.copyProperties(area,vo);
            vo.setParentId(area.getCityId());
            areaList.add(vo);
        });

        cityMapper.selectListByParams(new City()).forEach(city -> {
            AreaTreeVO vo = new AreaTreeVO();
            BeanUtils.copyProperties(city,vo);
            vo.setParentId(city.getProvinceId());
            List<AreaTreeVO> areaListTemp = areaList.stream()
                    .filter(area -> area.getParentId().equals(vo.getId()))
                    .collect(Collectors.toList());
            vo.setSubList(areaListTemp);
            cityList.add(vo);
        });

        provinceMapper.selectListByParams(new Province()).forEach(province -> {
            AreaTreeVO vo = new AreaTreeVO();
            BeanUtils.copyProperties(province,vo);
            List<AreaTreeVO> cityListTemp = cityList.stream()
                    .filter(city -> city.getParentId().equals(province.getId()))
                    .collect(Collectors.toList());
            vo.setSubList(cityListTemp);
            provinceList.add(vo);
        });
        return provinceList;
    }
}
