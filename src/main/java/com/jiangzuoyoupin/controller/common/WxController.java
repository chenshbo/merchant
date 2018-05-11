package com.jiangzuoyoupin.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jiangzuoyoupin.annotation.Auth;
import com.jiangzuoyoupin.base.WebResult;
import com.jiangzuoyoupin.config.WxPayConfig;
import com.jiangzuoyoupin.domain.LoginToken;
import com.jiangzuoyoupin.domain.WeChatUser;
import com.jiangzuoyoupin.req.PrepayReq;
import com.jiangzuoyoupin.req.WeChatUserLoginReq;
import com.jiangzuoyoupin.req.WeChatUserSaveReq;
import com.jiangzuoyoupin.service.UserService;
import com.jiangzuoyoupin.utils.HttpUtil;
import com.jiangzuoyoupin.utils.IdWorker;
import com.jiangzuoyoupin.utils.WebResultUtil;
import com.jiangzuoyoupin.vo.LoginTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 功能描述: 微信api controller
 *
 * @author: chenshangbo
 * @date: 2018/4/9 15:06
 */
@Api("小程序-微信API模块")
@RestController
@RequestMapping("common/wx")
public class WxController {

    @Autowired
    private UserService userService;

    @Value("${root.url}")
    private String rootUrl;
    @Value("${wx.secret}")
    private String wxAppSecret;

    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     * 功能模块: 微信授权登录
     *
     * @param req
     * @return com.jiangzuoyoupin.base.WebResult
     * @author chenshangbo
     * @date 2018-04-09 21:12:36
     */
    @ApiOperation(value = "微信授权登录", notes = "根据code换取openid和用户信息，返回用户登录的token")
    @ApiImplicitParam(name = "req", value = "授权登录对象", dataType = "WeChatUserLoginReq")
    @PostMapping(value = "/login")
    public WebResult<LoginTokenVO> login(@RequestBody WeChatUserLoginReq req) {
        String openid = "";

        // 通过code获取openid
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxPayConfig.getAppID()
                + "&secret=" + wxAppSecret
                + "&js_code=" + req.getCode()
                + "&grant_type=authorization_code";
        String tokenInfo = HttpUtil.doGet(url);
        JSONObject tokenJson = JSONObject.parseObject(tokenInfo);
        if (!tokenJson.containsKey("errcode")) {
            openid = tokenJson.getString("openid");// 授权用户唯一标识
        } else {
            return WebResultUtil.returnErrMsgResult("获取微信openid失败，" + tokenJson.getString("errmsg"));
        }

        // 拉取用户信息
        WeChatUser wechat = new WeChatUser();
        BeanUtils.copyProperties(req, wechat);
        wechat.setOpenId(openid);
        LoginToken loginToken = userService.generateToken(wechat);
        if (loginToken == null) {
            return WebResultUtil.returnErrMsgResult("微信授权登录失败");
        }
        LoginTokenVO vo = new LoginTokenVO();
        BeanUtils.copyProperties(loginToken, vo);
        return WebResultUtil.returnResult(vo);
    }
    @ApiOperation(value = "保存微信用户信息", notes = "保存微信用户信息")
    @ApiImplicitParam(name = "req", value = "请求对象", dataType = "WeChatUserSaveReq")
    @PostMapping(value = "/saveWxUserInfo")
    public WebResult saveWxUserInfo(@RequestBody WeChatUserSaveReq req) {
        WeChatUser user = new WeChatUser();
        BeanUtils.copyProperties(req,user);
        int count = userService.saveWxUserInfo(user);
        if(count == 0){
            return WebResultUtil.returnErrMsgResult("保存微信用户信息失败");
        }
        return WebResultUtil.returnResult();
    }


//    @ApiOperation(value = "支付预下单", notes = "支付预下单获取prepay_id")
//    @ApiImplicitParam(name = "req", value = "预下单对象", dataType = "PrepayReq")
//    @PostMapping(value = "/prepay")
//    public WebResult prepay(@RequestBody PrepayReq req) {
//        WXPay wxpay = new WXPay(wxPayConfig);
//        WeChatUser user = userService.getUserInfoById(req.getWeChatUserId());
//        if(user == null){
//            return WebResultUtil.returnErrMsgResult("用户不存在");
//        }
//        Map<String, String> data = new HashMap<>();
//        data.put("body", "JSAPI支付测试");// 商品描述
//        data.put("trade_type", "JSAPI"); // 交易类型 小程序指定JSAPI
//        data.put("out_trade_no", String.valueOf(idWorker.nextId())); // 商户订单号
//        data.put("device_info", "WEB"); // 设备号 默认WEB
//        data.put("fee_type", "CNY"); // 标价币种
//        data.put("total_fee", "1"); // 金额
//        data.put("spbill_create_ip", "47.98.217.177");
//        data.put("openid", user.getOpenId());
//        data.put("attach", "shopId=1");
//        data.put("notify_url", "https://zjyp.lyhangzhou.top/common/wx/notify");
//        try {
//            Map<String, String> resp = wxpay.unifiedOrder(data);
//            System.out.println(resp);
//            String result_code = resp.get("result_code");// 业务结果
//            String return_code = resp.get("return_code");// SUCCESS/FAIL
//            if ("FAIL".equals(return_code)) {
//                System.err.println("微信返回的交易状态不正确（result_code=" + return_code + "）");
//                return WebResultUtil.returnErrMsgResult(result_code);
//            }
//            HashMap<String, String> back = new HashMap<>();
//            String time = Long.toString(System.currentTimeMillis());
//            back.put("appId", wxPayConfig.getAppID());
//            back.put("timeStamp", time);
//            back.put("nonceStr", WXPayUtil.generateNonceStr());
//            back.put("signType", "MD5");
//            back.put("package", "prepay_id=" + resp.get("prepay_id"));
//            String sign2 =WXPayUtil.generateSignature(back,wxPayConfig.getKey());
//
//            JSONObject jsonObject =JSONObject.parseObject(JSON.toJSONString(back));
//            jsonObject.put("paySign", sign2);
//            System.out.println("二次签名后返回给前端的签名证书字符串是：" + sign2);
//            return WebResultUtil.returnResult(jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return WebResultUtil.returnErrMsgResult("预下单失败");
//        }
//    }

    @RequestMapping(value = "/notify")
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String result;//返回给微信的处理结果
        String inputLine;
        String notityXml = "";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        //微信给返回的东西
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
            return setXml("FAIL","xml获取失败");
        }
        if (StringUtils.isEmpty(notityXml)) {
            return setXml("FAIL","xml为空");
        }
        Map map = WXPayUtil.xmlToMap(notityXml);
        System.out.println(map);
        // 解析各种数据
        String appid = (String) map.get("appid");//应用ID
        String attach = (String) map.get("attach");//商家数据包
        String bank_type = (String) map.get("bank_type");//付款银行
        String cash_fee = (String) map.get("cash_fee");//现金支付金额
        String fee_type = (String) map.get("fee_type");//货币种类
        String is_subscribe = (String) map.get("is_subscribe");//是否关注公众账号
        String mch_id = (String) map.get("mch_id");//商户号
        String nonce_str = (String) map.get("nonce_str");//随机字符串
        String openid = (String) map.get("openid");//用户标识
        String out_trade_no = (String) map.get("out_trade_no");// 获取商户订单号
        String result_code = (String) map.get("result_code");// 业务结果
        String return_code = (String) map.get("return_code");// SUCCESS/FAIL
        String sign = (String) map.get("sign");// 获取签名
        String time_end = (String) map.get("time_end");//支付完成时间
        String total_fee = (String) map.get("total_fee");// 获取订单金额
        String trade_type = (String) map.get("trade_type");//交易类型
        String transaction_id = (String) map.get("transaction_id");//微信支付订单号
        if (!"SUCCESS".equals(return_code)) {
            System.err.println("微信返回的交易状态不正确（result_code=" + "SUCCESS" + "）");
            return setXml("fail", "微信返回的交易状态不正确（result_code=" + "SUCCESS" + "）");
        }
        WXPay wxpay = new WXPay(wxPayConfig);
        // 验证签名
        if (wxpay.isPayResultNotifySignatureValid(map)) {
            System.err.println("签名不一致！");
            return setXml("FAIL", "签名不一致！");
        }else {
            System.out.println("回调成功");
            return setXml("SUCCESS", "OK");
        }
        //如果成功写入数据库
//        if ("SUCCESS".equals("SUCCESS")) {// 如果微信返回的结果是success，则修改订单状态
//            //判断订单号是否重复
//            List<CapitalTable> userList = cpitalTableService.queryOrder(out_trade_no);
//            if (userList.size() > 0) {
//                result = setXml("fail", "订单号重复");
//            }else{
//                CapitalTable capitalTable = new CapitalTable();
//                UserAdmin userAdmin = new UserAdmin();
//                userAdmin.setId(6);
//                capitalTable.setCapital(total_fee);//金币
//                capitalTable.setState("1");//类型：1充值2提问3回答
//                capitalTable.setDatetime(new Date());//流入时间
//                capitalTable.setOrderNo(out_trade_no);
//                capitalTable.setUserAdmin(userAdmin);
//                cpitalTableService.insertCapital(capitalTable);
//            }
//        }
    }

    //通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" +
                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}
