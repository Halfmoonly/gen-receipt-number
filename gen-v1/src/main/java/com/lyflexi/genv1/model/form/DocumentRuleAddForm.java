package com.lyflexi.genv1.model.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author vdpublic969
 */
@Data
@ApiModel("DocumentRuleAddForm")
public class DocumentRuleAddForm {

    @ApiModelProperty("工厂代码")
    private String factoryCode;

    @ApiModelProperty("单据名称编码（数据字典明细编码）")
    @NotBlank(message = "单据名称编码不能为空！")
    private String documentNameCode;

    @ApiModelProperty("规则类型编码（数据字典明细编码）")
    @NotBlank(message = "规则类型编码不能为空！")
    private String ruleTypeCode;

    @ApiModelProperty("描述")
    private String describe;

    @Valid
    @ApiModelProperty("单据规则明细")
    private List<DocumentRuleItemAddForm> listItem;
}
