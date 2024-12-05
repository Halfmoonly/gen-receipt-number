package com.lyflexi.genv1.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("条码管理-单据规则主表")
@TableName("t_sys_document_rule")
public class DocumentRulePo extends BasePo {

    @ApiModelProperty("单据名称编码（数据字典明细编码）")
    private String documentNameCode;

    @ApiModelProperty("单据名称")
    private String documentName;

    @ApiModelProperty("规则类型编码（数据字典明细编码）")
    private String ruleTypeCode;

    @ApiModelProperty("条码长度")
    private Integer codeLength;

    @ApiModelProperty("描述字段")
    @TableField("`describe`")
    private String describe;

    @ApiModelProperty("启用状态（-1:新建；0:启用；1-禁用）")
    private Integer enableStatus;
}