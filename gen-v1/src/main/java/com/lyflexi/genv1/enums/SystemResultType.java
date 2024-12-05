package com.lyflexi.genv1.enums;

import lombok.Getter;

import java.lang.annotation.Annotation;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:17
 */
@Getter
public enum SystemResultType implements ResultType {

    SYSTEM_SUCCESS("000000", "handle.success"),

    SYSTEM_ERROR("-1", "system.error"),

    ;

    private String code;

    private String msg;

    SystemResultType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}