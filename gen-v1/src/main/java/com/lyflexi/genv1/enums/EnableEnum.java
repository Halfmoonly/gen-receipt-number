package com.lyflexi.genv1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:55
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public enum EnableEnum {

    NEWLY(-1, "新增"),

    ENABLE(0, "启用"),

    DISABLE(1, "禁用"),

    SUSPEND(2, "暂停"),

    FROZEN(3, "冻结");
    /**
     * 数据状态类型
     */
    private Integer code;
    /**
     * 数据状态描述信息
     */
    private String name;


    /**
     * 根据key值获取value
     * @param code
     * @return
     */
    public static String getName(int code) {
        return getEnable(code).getName();
    }

    public static EnableEnum getEnable(int code) {
        EnableEnum[] enableEnums = values();
        for (EnableEnum enableEnum : enableEnums) {
            if (code == enableEnum.getCode()) {
                return enableEnum;
            }
        }
        throw new RuntimeException("枚举类型错误！");
    }
}
