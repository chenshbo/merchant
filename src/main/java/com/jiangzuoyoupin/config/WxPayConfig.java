package com.jiangzuoyoupin.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Configuration
public class WxPayConfig implements WXPayConfig{

    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.mchid}")
    private String mchId;
    @Value("${wx.mchkey}")
    private String mchKey;

    private byte[] certData;

    public WxPayConfig() throws Exception {
//        File file = ResourceUtils.getFile("classpath:certificate/apiclient_cert.p12");
//        InputStream certStream = new FileInputStream(file);
        InputStream certStream = getClass().getClassLoader().getResourceAsStream("certificate/apiclient_cert.p12");
        this.certData = new byte[9999];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    public String getKey() {
        return mchKey;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}