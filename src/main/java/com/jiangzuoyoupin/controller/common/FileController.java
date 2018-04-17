package com.jiangzuoyoupin.controller.common;

import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.req.UploadFileReq;
import com.jiangzuoyoupin.utils.WebResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Api("公共-文件模块")
@RestController
@RequestMapping("common/file")
public class FileController {

    /**
     * 功能模块: 文件上传接口
     *
     * @param file
     * @return ResponseEntity<UploadResultModel>
     * @author chenshangbo
     * @date 2018-04-17 23:17:56
     */
    @ApiOperation(value = "文件上传接口", notes = "文件上传接口,文件大小不能大于2M")
    @ApiImplicitParam(name = "req", value = "文件上传请求对象", required = true, dataType = "UploadFileReq")
    @PostMapping(value = "/upload")
    public WebResult<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "E:\\IdeaProjects\\files\\";
        try {
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath+fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
        }
        //返回json
        return WebResultUtil.returnResult(filePath+fileName);
    }


    /**
     * 功能模块: 文件上传接口
     *
     * @param req
     * @return ResponseEntity<UploadResultModel>
     * @author chenshangbo
     * @date 2018-04-17 23:17:56
     */
    @ApiOperation(value = "文件上传接口", notes = "文件上传接口,文件大小不能大于2M")
    @ApiImplicitParam(name = "uploadFile", value = "文件上传请求对象", required = true, dataType = "UploadFileReq")
    @PostMapping(value = "/getUrl")
    public WebResult<String> getUrl(UploadFileReq req) {
        String fileName = req.getFile().getOriginalFilename();
        String filePath = "E:\\IdeaProjects\\files";
        try {
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath+fileName);
            out.write(req.getFile().getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
        }
        //返回json
        return WebResultUtil.returnResult(filePath+fileName);
    }

}
