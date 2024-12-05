package com.lyflexi.genv1.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DocumentRuleVo")
public class DocumentRuleVo {

    @ApiModelProperty("规则")
    private String id;

    @ApiModelProperty("单据名称id（数据字典明细id）")
    private String documentNameId;

    @ApiModelProperty("单据名称编码（数据字典明细编码）")
    private String documentNameCode;

    @ApiModelProperty("规则类型id（数据字典明细id）")
    private String ruleTypeId;

    @ApiModelProperty("规则类型编码（数据字典明细编码）")
    private String ruleTypeCode;

    @ApiModelProperty("备用字段1")
    private String spare1;

    @ApiModelProperty("备用字段2")
    private String spare2;

    @ApiModelProperty("备用字段3")
    private String spare3;

    @ApiModelProperty("备用字段4")
    private String spare4;

    @ApiModelProperty("单据规则明细")
    private List<DocumentRuleItemVo> listItem;

    @ApiModelProperty("单据名称")
    private String documentName;

    @ApiModelProperty("单据名称Pk")
    private String documentNamePk;

    @ApiModelProperty("规则类型名称")
    private String ruleTypeName;

    @ApiModelProperty("规则类型名称Pk")
    private String ruleTypeNamePk;

    @ApiModelProperty("条码长度")
    private Integer codeLength;

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

    @ApiModelProperty("数据状态(0:正常；1:删除)")
    private Integer dataStatus;

    @ApiModelProperty("启用状态（-1:新建；0:启用；1-禁用）")
    private Integer enableStatus;

    @ApiModelProperty("工厂代码")
    private String factoryCode;

    @ApiModelProperty("描述字段")
    private String describe;
}