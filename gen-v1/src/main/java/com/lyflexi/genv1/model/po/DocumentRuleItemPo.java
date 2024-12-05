package com.lyflexi.genv1.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyflexi.genv1.model.po.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("条码管理-单据规则明细表")
@TableName("t_sys_document_rule_item")
public class DocumentRuleItemPo extends BasePo {

    @ApiModelProperty("主表id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long documentRuleId;

    @ApiModelProperty("规则项编码（数据字典明细编码）")
    private String ruleItemCode;

    @ApiModelProperty("规则值编码")
    private String ruleValueCode;

    @ApiModelProperty("顺序")
    @JsonSerialize(using= ToStringSerializer.class)
    private Integer itemSort;

    @ApiModelProperty("字段长度")
    @JsonSerialize(using= ToStringSerializer.class)
    private Integer fieldLength;

    @ApiModelProperty("流水归零规则编码（数据字典明细编码）")
    private String zeroingRuleCode;

    @ApiModelProperty("长度不足补零方式编码（数据字典明细编码）")
    private String fillingModeCode;
}