package com.jiangzuoyoupin.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jiangzuoyoupin.config.WxPayConfig;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.IdWorker;
import com.jiangzuoyoupin.utils.WebResultUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private IdWorker idWorker;

    public WeChatUser getWeChatUserByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Token");
        return userService.getUserInfoByToken(accessToken);
    }

    public JSONObject prepay(Map<String,String> data){
        WXPay wxpay = new WXPay(wxPayConfig);
        data.put("trade_type", "JSAPI"); // 交易类型 小程序指定JSAPI
        data.put("device_info", "WEB"); // 设备号 默认WEB
        data.put("fee_type", "CNY"); // 标价币种
        data.put("spbill_create_ip", "47.98.217.177");
        data.put("notify_url", "https://zjyp.lyhangzhou.top/common/wx/notify");
        try {
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

}
