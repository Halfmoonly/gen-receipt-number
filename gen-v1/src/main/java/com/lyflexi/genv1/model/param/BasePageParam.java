package com.lyflexi.genv1.model.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:51
 */

@Data
public class BasePageParam<T> {

    @ApiModelProperty("当前页数")
    private long current = 1;

    @ApiModelProperty("当前页面每页显示的数量")
    private long size = 10;

    /**
     * 从param中获取page参数，用于分页查询参数
     *
     * @return IPage<?>
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public IPage<T> getPage() {
        return new Page<>(this.getCurrent(), this.getSize());
    }

    public static BasePageParam<?> of (long size) {
        BasePageParam<?> param = new BasePageParam<>();
        param.setSize(size);
        return param;
    }
}
