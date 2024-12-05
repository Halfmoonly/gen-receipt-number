package com.lyflexi.genv1.model.param;

import com.lyflexi.genv1.model.po.DocumentRulePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("DocumentRuleQueryParam")
public class DocumentRuleQueryParam extends BasePageParam<DocumentRulePo> {

    @ApiModelProperty("单据名称")
    private String documentName;

    @ApiModelProperty("启用状态(-1:新建；0:启用；1:禁用)")
    private Integer enableStatus;
}
