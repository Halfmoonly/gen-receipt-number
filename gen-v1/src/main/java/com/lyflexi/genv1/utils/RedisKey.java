package com.lyflexi.genv1.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:13
 */
public class RedisKey {
    private static final String KEY_CONCAT_CHAR = ":";

    public static String generator(String model,String... keys){
        StringBuffer sb = new StringBuffer();
        sb.append(model);
        sb.append(KEY_CONCAT_CHAR);
        sb.append(StringUtils.join(keys,KEY_CONCAT_CHAR));
        return sb.toString();
    }
}
