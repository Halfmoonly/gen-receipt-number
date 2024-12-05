package com.lyflexi.genv1.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:52
 */
@Data
@Accessors(chain = true)
public class BasePo {
    public final static String DEFAULT_USERNAME = "system";

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String addUserName;

    @ApiModelProperty("创建人工号")
    @TableField(fill = FieldFill.INSERT)
    private String addUserCode;

    @ApiModelProperty("更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String editUserName;

    @ApiModelProperty("更新人工号")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String editUserCode;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime editTime;

    @ApiModelProperty("数据状态(0:正常；1:删除)")
    @TableField(fill = FieldFill.INSERT)
    private Integer dataStatus;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("工厂代码")
    private String factoryCode;
}