package com.jiangzuoyoupin.req;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 功能模块: 文件请求req
 *
 * @author chenshangbo
 * @date 2018-04-17 23:16:42
 */
public class UploadFileReq implements Serializable{

    private static final long serialVersionUID = -5863053041778212172L;

    @ApiModelProperty(value = "文件")
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
