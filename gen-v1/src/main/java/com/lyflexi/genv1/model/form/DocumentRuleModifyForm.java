package com.lyflexi.genv1.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DocumentRuleModifyForm")
public class DocumentRuleModifyForm {

    @ApiModelProperty("规则ID")
    @NotNull(message = "规则ID不能为空")
    private String id;

    @ApiModelProperty("单据名称编码（数据字典明细编码）")
    @NotNull(message = "单据名称编码不能为空")
    private String documentNameCode;

    @ApiModelProperty("规则类型编码（数据字典明细编码）")
    private String ruleTypeCode;

    @ApiModelProperty("条码长度")
    private Integer codeLength;

    @ApiModelProperty("启用状态（-1:新建；0:启用；1-禁用）")
    private Integer enableStatus;

    @ApiModelProperty("描述字段")
    private String describe;

    @Valid
    @ApiModelProperty("单据规则明细")
    private List<DocumentRuleItemModifyForm> listItem;
}