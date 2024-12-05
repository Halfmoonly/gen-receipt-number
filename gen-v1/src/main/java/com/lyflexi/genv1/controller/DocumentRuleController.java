package com.lyflexi.genv1.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyflexi.genv1.enums.EnableForm;
import com.lyflexi.genv1.model.form.DocumentRuleAddForm;
import com.lyflexi.genv1.model.form.DocumentRuleModifyForm;
import com.lyflexi.genv1.model.form.VariableForm;
import com.lyflexi.genv1.model.param.DocumentRuleQueryParam;
import com.lyflexi.genv1.model.vo.DocumentRuleVo;
import com.lyflexi.genv1.result.Result;
import com.lyflexi.genv1.service.IDocumentRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:15
 */

@RestController
@RequestMapping("/system/documentRule")
@Api(value = "单据规则", tags = "单据规则")
@Slf4j
public class DocumentRuleController {

    @Autowired
    private IDocumentRuleService documentRuleService;

    @ApiOperation(value = "根据单据规则生成单据号", notes = "根据单据规则生成单据号")
    @PostMapping(value = "/generateIdNumber")
    @ApiImplicitParam(name = "documentNameCode", value = "单据名称编码", required = true, dataType = "String")
    public Result<String> generateIdNumber (@RequestParam(value ="documentNameCode") String documentNameCode, @RequestBody(required=false) List<VariableForm> list) {
        if (StrUtil.isBlank(documentNameCode)) throw new RuntimeException("LesErrorType.DOCUMENT_RULE_NAME_CODE");
        return Result.data(documentRuleService.generateIdNumber(documentNameCode, list));
    }

}