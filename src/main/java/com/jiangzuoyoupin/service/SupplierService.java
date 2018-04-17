package com.jiangzuoyoupin.service;

import com.github.pagehelper.PageHelper;
import com.jiangzuoyoupin.domain.UserSupplier;
import com.jiangzuoyoupin.dto.UserSupplierDto;
import com.jiangzuoyoupin.mapper.UserSupplierMapper;
import com.jiangzuoyoupin.req.SupplierQueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能模块: 供应商service
 *
 * @author chenshangbo
 * @date 2018-04-17 21:59:35
 */
@Service
public class SupplierService {

    @Autowired
    private UserSupplierMapper supplierMapper;

    /**
     * 功能模块: 分页条件查询供应商列表
     *
     * @param params
     * @return java.util.List<com.jiangzuoyoupin.dto.UserSupplierDto>
     * @author chenshangbo
     * @date 2018-04-17 23:07:49
     */
    public List<UserSupplierDto> selectSupplierList(SupplierQueryReq params) {
        if(params.getPagingRequired()){
            PageHelper.startPage(params.getCurrPage(),params.getPageRows());
        }
        return supplierMapper.selectByParams(params);
    }

    /**
     * 功能模块: 审核供应商
     *
     * @param userSupplier
     * @return int
     * @author chenshangbo
     * @date 2018-04-17 23:08:26
     */
    public int checkSupplier(UserSupplier userSupplier) {
        // 审核通过 分配登录账号和密码
        if(userSupplier.getStatus().intValue() == 1){
            UserSupplier supplier = supplierMapper.selectByPrimaryKey(userSupplier.getId());
            userSupplier.setLoginName(supplier.getMobileNo());
            userSupplier.setLoginPwd("123456");
        }
        return supplierMapper.updateByParams(userSupplier);
    }
}
