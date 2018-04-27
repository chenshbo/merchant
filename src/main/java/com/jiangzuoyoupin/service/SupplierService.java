package com.jiangzuoyoupin.service;

import com.github.pagehelper.PageHelper;
import com.jiangzuoyoupin.domain.Supplier;
import com.jiangzuoyoupin.dto.SupplierDto;
import com.jiangzuoyoupin.mapper.SupplierMapper;
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
    private SupplierMapper supplierMapper;

    /**
     * 功能模块: 分页条件查询供应商列表
     *
     * @param params
     * @return java.util.List<com.jiangzuoyoupin.dto.SupplierDto>
     * @author chenshangbo
     * @date 2018-04-17 23:07:49
     */
    public List<SupplierDto> selectSupplierList(SupplierQueryReq params) {
        if(params.getPagingRequired()){
            PageHelper.startPage(params.getCurrPage(),params.getPageRows());
        }
        return supplierMapper.selectByParams(params);
    }

    /**
     * 功能模块: 审核供应商
     *
     * @param Supplier
     * @return int
     * @author chenshangbo
     * @date 2018-04-17 23:08:26
     */
    public int checkSupplier(Supplier Supplier) {
        // 审核通过 分配登录账号和密码
        if(Supplier.getStatus().intValue() == 1){
            Supplier supplier = supplierMapper.selectByPrimaryKey(Supplier.getId());
            Supplier.setLoginName(supplier.getMobileNo());
            Supplier.setLoginPwd("123456");
        }
        return supplierMapper.updateByParams(Supplier);
    }
}
