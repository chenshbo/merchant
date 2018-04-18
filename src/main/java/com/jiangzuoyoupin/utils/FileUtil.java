package com.jiangzuoyoupin.utils;

import java.util.Date;

public final class FileUtil {

    /**
     * 功能模块: 根据时间生成新的文件名
     *
     * @param fileName
     * @return java.lang.String
     * @author chenshangbo
     * @date 2018-04-18 22:22:17
     */
    public static String getNewFileName(String fileName) {
        return DateUtil.formatYMDHMSSS(new Date()) + RandomUtils.getRandomNumber(0, 2) + "." + getFileSuffix(fileName);
    }


    /**
     * 输入文件全称, 输出文件后缀
     * 例如： abcde.txt -> txt Or abc.cde$fgh.mp4 -> mp4
     */
    public static String getFileSuffix(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }
}
