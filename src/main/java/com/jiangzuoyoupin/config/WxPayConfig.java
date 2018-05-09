//package com.jiangzuoyoupin.config;
//
//import com.github.wxpay.sdk.WXPayConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.*;
//
//@Configuration
//public class WxPayConfig implements WXPayConfig{
//
//    @Value("${wx.appid}")
//    private String appId;
//    @Value("${wx.mchid}")
//    private String mchId;
//    @Value("${wx.mchkey}")
//    private String mchKey;
//
//    private byte[] certData;
//
//    public WxPayConfig() throws Exception {
//        String certPath = "/path/to/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
//    }
//
//    public String getAppID() {
//        return appId;
//    }
//
//    public String getMchID() {
//        return mchId;
//    }
//
//    public String getKey() {
//        return mchKey;
//    }
//
//    public InputStream getCertStream() {
//        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
//        return certBis;
//    }
//
//    public int getHttpConnectTimeoutMs() {
//        return 8000;
//    }
//
//    public int getHttpReadTimeoutMs() {
//        return 10000;
//    }
//}