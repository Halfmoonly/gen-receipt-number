package com.lyflexi.genv1.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: SequenceGeneratorPo
 * @Description: 在此添加类描述
 * @Author: ma.honggang
 * @Version: v1.0
 * @Date: 2022/10/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("序列生成")
@TableName("t_sys_sequence_generator")
public class SequenceGeneratorPo extends BasePo {

    @ApiModelProperty("序列Key")
    @TableField("`key`")
    private String key;

    @ApiModelProperty("序列Value")
    @TableField("`value`")
    private Long value;

    @ApiModelProperty("序列Version")
    @TableField("`version`")
    private Long version;

    @ApiModelProperty("序列Expire")
    @TableField("`expire`")
    private Long expire;
}
