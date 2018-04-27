package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.Module;

public interface ModuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
}