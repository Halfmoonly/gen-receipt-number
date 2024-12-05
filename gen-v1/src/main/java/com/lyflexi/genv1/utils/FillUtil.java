package com.lyflexi.genv1.utils;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:11
 */

/**
 * 字符串自动补零工具类
 */
public class FillUtil {
    /**
     * 前面补零
     * @param fieldLength
     * @param arg
     * @return
     */
    public static String zeroFillFront(int fieldLength, String arg) {
        if (arg != null && arg.length() < fieldLength) {
            StringBuilder tmpStr = new StringBuilder();
            for (int i = 0, length = fieldLength - arg.length(); i < length; i++) {
                tmpStr.append("0");
            }
            return tmpStr.append(arg).toString();
        }
        return arg;
    }

    /**
     * 后面补零
     * @param fieldLength
     * @param arg
     * @return
     */
    public static String zeroFillBack(int fieldLength, String arg) {
        if (arg != null && arg.length() < fieldLength) {
            StringBuilder tmpStr = new StringBuilder();
            tmpStr.append(arg);
            for (int i = 0, length = fieldLength - arg.length(); i < length; i++) {
                tmpStr.append("0");
            }
            return tmpStr.toString();
        }
        return arg;
    }

    public static void main(String[] args) {
        String oldStr = "45654";
        System.out.println(zeroFillFront(10, oldStr));
        System.out.println(zeroFillBack(10, oldStr));
    }
}
