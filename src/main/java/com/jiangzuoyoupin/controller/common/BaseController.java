package com.jiangzuoyoupin.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.jiangzuoyoupin.config.WxPayConfig;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.IdWorker;
import com.jiangzuoyoupin.utils.WebResultUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能模块: 基础controller
 *
 * @author chenshangbo
 * @date 2018-04-21 00:17:16
 */
public class BaseController {

    @Autowired
    public UserService userService;

    @Autowired
    private WxPayConfig wxPayConfig;

    public WeChatUser getWeChatUserByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Token");
        return userService.getUserInfoByToken(accessToken);
    }

    public JSONObject prepay(Map<String,String> data){
        try {
            WXPay wxpay = new WXPay(wxPayConfig);
            data.put("trade_type", "JSAPI"); // 交易类型 小程序指定JSAPI
            data.put("device_info", "WEB"); // 设备号 默认WEB
            data.put("fee_type", "CNY"); // 标价币种
            data.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
            data.put("notify_url", "https://zjyp.lyhangzhou.top/common/wx/notify");
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            String return_code = resp.get("return_code");// SUCCESS/FAIL
            if ("FAIL".equals(return_code)) {
                System.err.println("微信返回的交易状态不正确（result_code=" + return_code + "）");
                return null;
            }
            HashMap<String, String> back = new HashMap<>();
            String time = Long.toString(System.currentTimeMillis());
            back.put("appId", wxPayConfig.getAppID());
            back.put("timeStamp", time);
            back.put("nonceStr", WXPayUtil.generateNonceStr());
            back.put("signType", "MD5");
            back.put("package", "prepay_id=" + resp.get("prepay_id"));
            String sign2 = WXPayUtil.generateSignature(back, wxPayConfig.getKey());

            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(back));
            jsonObject.put("paySign", sign2);
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> mchPay2Wallet(Map<String, String> reqData){
        try {
            WXPay wxpay = new WXPay(wxPayConfig);
            reqData.put("mch_appid", wxPayConfig.getAppID());
            reqData.put("mchid", wxPayConfig.getMchID());
            reqData.put("nonce_str", WXPayUtil.generateNonceStr());
            reqData.put("check_name", "NO_CHECK");
            reqData.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
            reqData.put("sign", WXPayUtil.generateSignature(reqData, wxPayConfig.getKey(), WXPayConstants.SignType.MD5));
            System.out.println("mchPay2Wallet---"+JSONObject.toJSONString(reqData).toString());
            String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
            String respXml = wxpay.requestWithCert(url, reqData, wxPayConfig.getHttpConnectTimeoutMs(), wxPayConfig.getHttpReadTimeoutMs());
            return wxpay.processResponseXml(respXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> mchPay2Card(Map<String, String> reqData){
        try {
            WXPay wxpay = new WXPay(wxPayConfig);
            reqData.put("mch_id", wxPayConfig.getMchID());
            reqData.put("nonce_str", WXPayUtil.generateNonceStr());
            reqData.put("sign", WXPayUtil.generateSignature(reqData, wxPayConfig.getKey(), WXPayConstants.SignType.MD5));
            System.out.println("mchPay2Card---"+JSONObject.toJSONString(reqData).toString());
            String url = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
            String respXml = wxpay.requestWithCert(url, reqData, wxPayConfig.getHttpConnectTimeoutMs(), wxPayConfig.getHttpReadTimeoutMs());
            return WXPayUtil.xmlToMap(respXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
