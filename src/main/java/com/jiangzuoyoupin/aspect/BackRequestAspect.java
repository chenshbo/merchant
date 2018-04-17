//package com.jiangzuoyoupin.aspect;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jiangzuoyoupin.annotation.Auth;
//import com.jiangzuoyoupin.service.UserService;
//import com.jiangzuoyoupin.utils.WebResultUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.Random;
//
///**
// * 功能描述: 运营后台请求拦截切面
// *
// * @author: chenshangbo
// * @date: 2018-04-13 14:42:56
// */
//@Aspect
//@Component
//public class BackRequestAspect {
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private UserService userService;
//
//    @Pointcut("execution(public * com.jiangzuoyoupin.controller.back.*.*(..)))")
//    private void authAround() {
//    }
//
//    /**
//     * 功能描述: 小程序请求拦截 验证登录
//     *
//     * @param call
//     * @return: java.lang.Object
//     * @since: 1.0.0
//     * @author: chenshangbo
//     * @date: 2018-04-13 14:42:02
//     */
//    @Around("authAround()")
//    public Object doAround(ProceedingJoinPoint call) throws Throwable {
//        if (call.getArgs().length == 0) {
//            return call.proceed();
//        }
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String reqParams = JSONObject.toJSONString(call.getArgs());
//        String api = request.getRequestURI();
//
//        String reqId = getRandom(10);
//        String ip = getIpAddress(request);
//        logger.info("[" + reqId + ",REQUEST]" + api + "," + ip + "[" + reqParams + "]");
//        Date startDate = new Date();
//        boolean requestResult = false;
//        try {
//            //1.登录检查
//            if (checkHasAnnotation(call)) {
//                String accessToken = request.getHeader("access_token");
//                if(StringUtils.isEmpty(accessToken) || !userService.checkLoginToken(accessToken)){
//                    return WebResultUtil.returnErrMsgResult("登录信息失效，请重新登录");
//                }
//            }
//            //2.请求处理
//            Object object = call.proceed();
//            logger.info("[" + reqId + ",RESPONSE]" + JSONObject.toJSONString(object));
//            requestResult = true;
//            return object;
//        } catch (Exception e) {
//            logger.error("服务请求处理异常", e);
//            return WebResultUtil.returnErrMsgResult("服务请求处理异常");
//        } finally {
//            Date endDate = new Date();
//            long runTime = endDate.getTime() - startDate.getTime();
//            String log = String.format("[%s]PERFORMANCE:%s,%s,耗时：%sms", reqId, api, requestResult, runTime);
//            logger.info(log);
//        }
//    }
//
//    /**
//     * 功能描述: 根据Auth注解 检查是否需要校验登录
//     *
//     * @param pjp
//     * @return: java.lang.annotation.Annotation
//     * @since: 1.0.0
//     * @author: chenshangbo
//     * @date: 2018-04-13 15:05:12
//     */
//    private boolean checkHasAnnotation(ProceedingJoinPoint pjp) {
//        Class<?> classTarget = pjp.getTarget().getClass();
//        if(classTarget.getAnnotation(Auth.class) != null){
//            return true;
//        }
//        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
//        Method method = joinPointObject.getMethod();
//        if(method.getAnnotation(Auth.class) != null){
//            return true;
//        }
//        return false;
//    }
//
//    private static String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//
//    private static Random random = new Random();
//
//    private static String getRandom(int length) {
//        StringBuilder ret = new StringBuilder();
//        for (int i = 0; i < length; ++i) {
//            boolean isChar = random.nextInt(2) % 2 == 0;
//            if (isChar) {
//                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
//                ret.append((char) (choice + random.nextInt(26)));
//            } else {
//                ret.append(Integer.toString(random.nextInt(10)));
//            }
//        }
//        return ret.toString();
//    }
//
//}
