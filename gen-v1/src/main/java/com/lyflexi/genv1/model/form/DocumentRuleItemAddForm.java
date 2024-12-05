package com.lyflexi.genv1.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DocumentRuleItemAddForm")
public class DocumentRuleItemAddForm {

    @ApiModelProperty("工厂代码")
    private String factoryCode;

    @ApiModelProperty("规则项编码（数据字典明细编码）")
    @NotBlank(message = "规则项编码不能为空！")
    private String ruleItemCode;

    @ApiModelProperty("规则值编码")
    private String ruleValueCode;

    @ApiModelProperty("顺序")
    @NotNull(message = "规则项顺序不能为空！")
    private Integer itemSort;

    @ApiModelProperty("字段长度")
    @NotNull(message = "字段长度不能为空！")
    private Integer fieldLength;

    @ApiModelProperty("流水归零规则编码（数据字典明细编码）")
    private String zeroingRuleCode;

    @ApiModelProperty("长度不足补零方式编码（数据字典明细编码）")
    private String fillingModeCode;

}