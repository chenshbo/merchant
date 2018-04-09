package com.jiangzuoyoupin.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    // 整数四舍五入
    public static int round(int width) {
        return Math.round(((float) width) / 10) * 10;
    }

    /**
     * 产生随机的六位数
     * @return
     */
    public static String getVerifyCode(){
        Random rad = new Random();
        return rad.nextInt(1000000)+"";
    }


    /**
     * 版本比较
     * baseVer >  compareVer return -1
     * baseVer <  compareVer return 1
     * baseVer == compareVer return 0
     *
     * @throws Exception
     */
    public static int versionCompare(String baseVer, String compareVer) throws RuntimeException {
        Pattern p = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+).*$");

        Matcher bMatch = p.matcher(baseVer);
        Matcher cMatch = p.matcher(compareVer);


//        if (!bMatch.find()
//                || (bMatch.find() && bMatch.groupCount() != 3)) {
//            throw new Exception("版本格式不正确: " + baseVer);
//        }
//
//        if (!cMatch.find()
//                || (cMatch.find() && cMatch.groupCount() != 3)) {
//            throw new Exception("版本格式不正确: " + compareVer);
//        }


        if (bMatch.find() && bMatch.groupCount() >= 3
                && cMatch.find() && cMatch.groupCount() >= 3) {

            int bv0 = Integer.valueOf(bMatch.group(1));
            int bv1 = Integer.valueOf(bMatch.group(2));
            int bv2 = Integer.valueOf(bMatch.group(3));

            int cv0 = Integer.valueOf(cMatch.group(1));
            int cv1 = Integer.valueOf(cMatch.group(2));
            int cv2 = Integer.valueOf(cMatch.group(3));

            if (bv0 > cv0) return -1;
            else if (bv0 < cv0) return 1;
            else {

                if (bv1 > cv1) return -1;
                else if (bv1 < cv1) return 1;
                else {

                    if (bv2 > cv2) return -1;
                    else if (bv2 < cv2) return 1;
                    
                }
            }

            return 0;
        } else {
            throw new RuntimeException("版本格式不正确");
        }

    }


    public static void main(String[] args) throws Exception {
        System.out.println(getVerifyCode());
//        System.out.println(versionCompare("1.1.24", "1.1.24.1"));
    }


}
