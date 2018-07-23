package com.jiangzuoyoupin.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMap {
	/**
	 * 数据缓存map
	 */
	private static Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();

	/**
	 * 将一个key、value值放入内存缓存
	 *
	 * @param key
	 * @param val
	 */
	public static void put(String key, Object val) {
		dataMap.put(key, val);
	}

	/**
	 * 从缓存中获取一个key的数据(若过期返回null)
	 *
	 * @param cacheKey
	 * @return
	 */
	public static Object get(String cacheKey) {
		return CacheMap.dataMap.get(cacheKey);
	}

	/**
	 * 从缓存中删除key
	 *
	 * @param key
	 */
	public static void del(String key) {
		CacheMap.dataMap.remove(key);
	}
}