/**
 * 
 */
package com.jiangzuoyoupin.utils;

import java.util.UUID;

/**
 * 功能模块: token工具类
 *
 * @author chenshangbo
 * @date 2018-04-12 23:18:25
 */
public class TokenUtil {

   /**
	* 
	* @Description: 生成accessToken
	* @return String    返回类型 
	* @throws
	*/
	public static String generateToken(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
