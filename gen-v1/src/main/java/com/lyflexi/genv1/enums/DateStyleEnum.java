package com.lyflexi.genv1.enums;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:00
 */


/**
 * 时间枚举类
 *
 * @author qdh
 */
public enum DateStyleEnum {
    /**
     * 时间格式
     */
    MM_DD("MM-dd"),
    /**
     * 时间格式
     */
    YYYY("yyyy"),
    /**
     * 时间格式
     */
    YYYY_MM("yyyy-MM"),
    /**
     * 时间格式
     */
    YYYY_MM_DD("yyyy-MM-dd"),

    /**
     *  时间格式: yyyy年MM月dd日
     */
    YYYY_MM_DD_CC("yyyy年MM月dd日"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM("MM-dd HH:mm"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    /**
     * 时间格式
     */
    MM_DD_EN("MM/dd"),
    /**
     * 时间格式
     */
    YYYY_MM_EN("yyyy/MM"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_EN("yyyy/MM/dd"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM_EN("MM/dd HH:mm"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),
    /**
     * 时间格式
     */
    MM_DD_CN("MM月dd日"),
    /**
     * 时间格式
     */
    YYYY_MM_CN("yyyy年MM月"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_CN("yyyy年MM月dd日"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
    /**
     * 时间格式
     */
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
    /**
     * 时间格式
     */
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

    /**
     * 时间格式
     */
    HH_MM("HH:mm"),
    /**
     * 时间格式
     */
    HH_MM_SS("HH:mm:ss");

    /**
     * @param 获取时间
     * @return
     */
    private String value;

    /**
     * @param value
     * @return value
     */
    DateStyleEnum(String value) {
        this.value = value;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }
}

