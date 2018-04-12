package com.jiangzuoyoupin.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    /**
     * 功能模块: vo转换
     *
     * @param fromObj
     * @param clazz
     * @return T
     * @author chenshangbo
     * @date 2018-04-12 23:37:41
     */
    public static <T> T convert2vo(Object fromObj,Class<T> clazz){
        T vo = null;
        try {
            vo = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(fromObj, vo);
        return vo;
    }

    /**
     * Description: poList转化voList方法
     *
     * @param list  poList
     * @param clazz 需要转换的VO对象
     * @return java.util.List<T> voList
     * @author chenshangbo
     * @date 2017-12-15 16:22:00
     */
    public static <T> List<T> poList2voList(List<? extends Object> list, Class<T> clazz) {
        return list.stream().map(res -> {
            T vo = null;
            try {
                vo = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(res, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
