package com.lyflexi.genv1.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:56
 */

/*批量禁用与删除*/
@Data
@ApiModel
public class EnableForm {

    @ApiModelProperty("启用状态（-1:新建，0：启用；1：禁用）")
    private Integer enableStatus;

    @ApiModelProperty("数据状态(0:正常；1:删除)")
    private Integer dataStatus;

    @ApiModelProperty("产品料号+产品版本的集合")
    private List<ProdLineEnable> prodLineEnables;

    @Data
    public static class ProdLineEnable{
        @ApiModelProperty("产品料号")
        @NotBlank(message = "productMaterialCode必传")
        private String productMaterialCode ;
        @NotBlank(message = "productMaterialVersion必传")
        @ApiModelProperty("产品版本")
        private String productMaterialVersion ;
    }

    @ApiModelProperty("ID集合")
    @NotNull(message = "ID集合不能为空")
    private List<Long> ids;

    @ApiModelProperty("工厂编码")
    private String factoryCode;

}