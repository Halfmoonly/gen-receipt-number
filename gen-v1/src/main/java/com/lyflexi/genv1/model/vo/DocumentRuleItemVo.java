package com.lyflexi.genv1.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DocumentRuleItemVo")
public class DocumentRuleItemVo {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("主表id")
    private Long documentRuleId;

    @ApiModelProperty("规则项id（数据字典明细id）")
    private String ruleItemId;

    @ApiModelProperty("规则项编码（数据字典明细编码）")
    private String ruleItemCode;

    @ApiModelProperty("规则值id（数据字典明细id或手动输入）")
    private String ruleValueId;

    @ApiModelProperty("规则值编码")
    private String ruleValueCode;

    @ApiModelProperty("顺序")
    private Integer itemSort;

    @ApiModelProperty("字段长度")
    private Integer fieldLength;

    @ApiModelProperty("流水归零规则id（数据字典明细id）")
    private String zeroingRuleId;

    @ApiModelProperty("流水归零规则编码（数据字典明细编码）")
    private String zeroingRuleCode;

    @ApiModelProperty("长度不足补零方式id（数据字典明细id）")
    private String fillingModeId;

    @ApiModelProperty("长度不足补零方式编码（数据字典明细编码）")
    private String fillingModeCode;

    @ApiModelProperty("备用字段1")
    private String spare1;

    @ApiModelProperty("备用字段2")
    private String spare2;

    @ApiModelProperty("备用字段3")
    private String spare3;

    @ApiModelProperty("备用字段4")
    private String spare4;

    @ApiModelProperty("规则项名称")
    private String ruleItemName;

    @ApiModelProperty("规则项名称Pk")
    private String ruleItemNamePk;

    @ApiModelProperty("规则值名称")
    private String ruleValueName;

    @ApiModelProperty("规则值名称pk")
    private String ruleValueNamePk;

    @ApiModelProperty("流水归零规则名称")
    private String zeroingRuleName;

    @ApiModelProperty("流水归零规则名称pk")
    private String zeroingRuleNamePk;

    @ApiModelProperty("长度不足补零方式名称")
    private String fillingModeName;

    @ApiModelProperty("长度不足补零方式名称pk")
    private String fillingModeNamePk;

    @ApiModelProperty("创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty("创建人Id")
    private String addUserId;

    @ApiModelProperty("创建人")
    private String addUserName;

    @ApiModelProperty("创建人工号")
    private String addUserCode;

    @ApiModelProperty("更新人ID")
    private String editUserId;

    @ApiModelProperty("更新人")
    private String editUserName;

    @ApiModelProperty("更新人工号")
    private String editUserCode;

    @ApiModelProperty("更新时间")
    private LocalDateTime editTime;

    @ApiModelProperty("启用状态（-1:新建；0:启用；1-禁用）")
    private Integer enableStatus;

    @ApiModelProperty("工厂代码")
    private String factoryCode;

    @ApiModelProperty("描述字段")
    private String describe;

}