package com.jiangzuoyoupin.controller.back;

import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebPageResult;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.domain.UserSupplier;
import com.jiangzuoyoupin.dto.UserSupplierDto;
import com.jiangzuoyoupin.req.SupplierCheckReq;
import com.jiangzuoyoupin.req.SupplierQueryReq;
import com.jiangzuoyoupin.service.SupplierService;
import com.jiangzuoyoupin.utils.ConvertUtils;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.UserSupplierVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能描述: 后台用户审核controller
 *
 * @author: chenshangbo
 * @date: 2018-04-16 11:32:52
 */
@Auth
@Api("后台-审核模块")
@RestController
@RequestMapping("back/audit")
public class AuditController {

    @Value("${root.url}")
    private String rootUrl;

    @Autowired
    private SupplierService supplierService;

    /**
     * 功能描述: 条件查询供应商列表
     *
     * @param req
     * @return: com.jiangzuoyoupin.base.WebResult
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-17 21:07:13
     */
    @ApiOperation(value = "供应商列表", notes = "条件查询供应商列表")
    @ApiImplicitParam(name = "req", value = "条件查询对象", dataType = "SupplierQueryReq")
    @PostMapping(value = "/supplier/list")
    public WebPageResult<List<UserSupplierVO>> selectSupplierList(@RequestBody SupplierQueryReq req) {
        List<UserSupplierDto> supplierList = supplierService.selectSupplierList(req);
        List<UserSupplierVO> voList = supplierList.stream().map(res -> {
            UserSupplierVO vo = new UserSupplierVO();
            BeanUtils.copyProperties(res, vo);
            vo.setBusinessLicenseImage(rootUrl + vo.getBusinessLicenseImage());
            return vo;
        }).collect(Collectors.toList());
        return WebResultUtil.returnPageResult(voList, supplierList, req.getPagingRequired());
    }

    /**
     * 功能描述: 条件查询供应商列表
     *
     * @param req
     * @return: com.jiangzuoyoupin.base.WebResult
     * @since: 1.0.0
     * @author: chenshangbo
     * @date: 2018-04-17 21:07:13
     */
    @ApiOperation(value = "供应商列表", notes = "条件查询供应商列表")
    @ApiImplicitParam(name = "req", value = "审核对象", dataType = "SupplierCheckReq")
    @PostMapping(value = "/supplier/check")
    public WebResult checkSupplier(@RequestBody SupplierCheckReq req) {
        UserSupplier params = new UserSupplier();
        BeanUtils.copyProperties(req, params);
        int count = supplierService.checkSupplier(params);
        if (count > 0) {
            return WebResultUtil.returnErrMsgResult("审核供应商失败");
        }
        return WebResultUtil.returnResult();
    }


}
