package com.lyflexi.genv1.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("VariableForm")
@NoArgsConstructor
@AllArgsConstructor
public class VariableForm {

    @ApiModelProperty("变量（数据字典明细编码）")
    private String variable;

    @ApiModelProperty("变量值")
    private String variableValue;

    public static VariableForm of (String variable, String variableValue) {
        return new VariableForm(variable, variableValue);
    }
}
