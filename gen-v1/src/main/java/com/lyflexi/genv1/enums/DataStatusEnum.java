package com.lyflexi.genv1.enums;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:58
 */
@Getter
@Api("")
@NoArgsConstructor
@AllArgsConstructor
public enum DataStatusEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除"),
    ;


    /**
     * 数据状态类型
     */
    private Integer code;
    /**
     * 数据状态描述信息
     */
    private String name;

}