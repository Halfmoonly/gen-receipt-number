package com.lyflexi.genv1.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 13:58
 */

public enum DocumentRuleEnums {
    ;

    @Getter
    @ApiModel("工单来源枚举-DocumentRuleEnums:DocumentName")
    @NoArgsConstructor
    @AllArgsConstructor
    public enum DocumentName {
        DOCUMENT_ORDER("drule_order_code","工单号"),
        DOCUMENT_SCHEDULE("drule_schedule_code","排程号"),
        DEMAND_CODE("drule_demand_code","需求单号"),
        DEMANDING_CODE("drule_demanding_code","需求单号"),
        PICKING_CODE("drule_picking_code","拣配单号");

        private String code;
        private String name;
    }

    @Getter
    @ApiModel("规则项目类型枚举-DocumentRuleEnums:RuleItemCode")
    @NoArgsConstructor
    @AllArgsConstructor
    public enum RuleItemCode {
        FIXED_VALUE("fixed_value","固定值"),
        VARIABLE("variable","变量"),
        DATE("date","日期"),
        TURNOVER("turnover","流水"),
        ;

        private String code;
        private String name;
    }

    @Getter
    @ApiModel("补零填充方式枚举-DocumentRuleEnums:FillingMode")
    @NoArgsConstructor
    @AllArgsConstructor
    public enum FillingMode {
        FRONT("front","前置补零"),
        POSTPOSE("postpose","后置补零"),
        ;
        private String code;
        private String name;
    }

    @Getter
    @ApiModel("流水归零方式枚举-DocumentRuleEnums:TurnoverMode")
    @NoArgsConstructor
    @AllArgsConstructor
    public enum TurnoverMode {
        DAY("day","按天"),
        WEEK("week","按周"),
        MONTH("month","按月"),
        YEAR("year","按年"),
        ;
        private String code;
        private String name;
    }
}
