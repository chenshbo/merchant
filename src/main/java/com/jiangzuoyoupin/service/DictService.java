package com.jiangzuoyoupin.service;

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

    public List<Province> selectProvinceList(Province province) {
        return provinceMapper.selectListByParams(province);
    }
}
