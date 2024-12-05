package com.lyflexi.genv1.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyflexi.genv1.dao.DocumentRuleMapper;
import com.lyflexi.genv1.enums.DataStatusEnum;
import com.lyflexi.genv1.enums.DocumentRuleEnums;
import com.lyflexi.genv1.enums.EnableEnum;
import com.lyflexi.genv1.enums.EnableForm;

import com.lyflexi.genv1.model.form.VariableForm;
import com.lyflexi.genv1.model.param.DocumentRuleQueryParam;
import com.lyflexi.genv1.model.po.DocumentRuleItemPo;
import com.lyflexi.genv1.model.po.DocumentRulePo;
import com.lyflexi.genv1.model.vo.DocumentRuleItemVo;
import com.lyflexi.genv1.model.vo.DocumentRuleVo;
import com.lyflexi.genv1.service.IDocumentRuleItemService;
import com.lyflexi.genv1.service.IDocumentRuleService;
import com.lyflexi.genv1.service.ISequenceGeneratorService;
import com.lyflexi.genv1.utils.FillUtil;
import com.lyflexi.genv1.utils.RedisKey;
import com.lyflexi.genv1.utils.VOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentRuleServiceImpl extends ServiceImpl<DocumentRuleMapper, DocumentRulePo> implements IDocumentRuleService {

    private static final String TYPE = "document";
    public static final String LES_MODULE = "les-core";

    @Autowired
    private IDocumentRuleItemService documentRuleItemService;

    @Autowired
    private ISequenceGeneratorService<String, Long> sequenceGeneratorService;




    @Override
    public boolean delete (List<Long> ids) {
        boolean r1 = this.update(Wrappers.<DocumentRulePo>lambdaUpdate()
                .in(DocumentRulePo::getId, ids)
                .set(DocumentRulePo::getDataStatus, DataStatusEnum.DELETE.getCode()));
        boolean r2 = documentRuleItemService.deleteByRuleIds(ids);
        return r1 && r2;
    }





    @Override
    public boolean enable(List<Long> ids, EnableEnum enable) {
        return this.update(Wrappers.<DocumentRulePo>lambdaUpdate().in(DocumentRulePo::getId, ids)
                .set(DocumentRulePo::getEnableStatus, enable.getCode()));
    }


    /**
     * 根据单据规则生成单据号
     * @param documentNameCode
     * @param list
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateIdNumber(String documentNameCode, List<VariableForm> list) {
        DocumentRulePo documentRulePo = this.getOne(Wrappers.<DocumentRulePo>lambdaQuery()
                .eq(DocumentRulePo::getDataStatus, DataStatusEnum.NORMAL.getCode())
                .eq(DocumentRulePo::getEnableStatus, EnableEnum.ENABLE.getCode())
                .eq(DocumentRulePo::getDocumentNameCode, documentNameCode));
        Assert.notNull(documentRulePo," LesErrorType.RULE_ONLY_ONE_EXITS.getMesg()");
        StringBuilder idNumberSb = new StringBuilder();
        List<DocumentRuleItemPo> itemList = documentRuleItemService.listByRuleId(documentRulePo.getId());
        // 过滤传入变量值是否符合规则中配置的所有变量
        boolean variableFlag = true;
        for (DocumentRuleItemPo po : itemList) {
            if (DocumentRuleEnums.RuleItemCode.FIXED_VALUE.getCode().equals(po.getRuleItemCode())) {
                // 如果为固定值类型，直接拼接固定值
                idNumberSb.append(po.getRuleValueCode());
            } else if (DocumentRuleEnums.RuleItemCode.VARIABLE.getCode().equals(po.getRuleItemCode())) {
                // 如果为变量类型，拼接传入变量并根据补零方式填充
                if (CollectionUtils.isEmpty(list)) throw new RuntimeException("LesErrorType.VARIABLE_VALUE_ERROR");
                List<VariableForm> data = list.stream().filter(form -> StrUtil.isNotBlank(form.getVariable()) && po.getRuleValueCode().equals(form.getVariable())).collect(Collectors.toList());
                // 同一种变量类型只能有一个，传入多个时也不符合规则
                if (data.size() == 1) {
                    VariableForm form = data.get(0);
                    if (StrUtil.isBlank(form.getVariableValue()))
                        throw new RuntimeException("LesErrorType.VARIABLE_VALUE_IS_NULL");
                    if (po.getFieldLength() != null) {
                        String fillStr = StrUtil.EMPTY;
                        if (DocumentRuleEnums.FillingMode.FRONT.getCode().equals(po.getFillingModeCode())) {
                            fillStr = FillUtil.zeroFillFront(po.getFieldLength(), form.getVariableValue());
                        } else if (DocumentRuleEnums.FillingMode.POSTPOSE.getCode().equals(po.getFillingModeCode())) {
                            fillStr = FillUtil.zeroFillBack(po.getFieldLength(), form.getVariableValue());
                        }
                        idNumberSb.append(fillStr);
                    } else {
                        idNumberSb.append(form.getVariableValue());
                    }
                } else {
                    // 如果配置的单据规则变量，不在传入的变量列表中，则直接报错返回
                    variableFlag = false;
                    break;
                }
            } else if (DocumentRuleEnums.RuleItemCode.DATE.getCode().equals(po.getRuleItemCode())) {
                // 如果为日期类型，拼接对应日期格式字符串
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(po.getRuleValueCode());
                    idNumberSb.append(sdf.format(new Date()));
                } catch (Exception e) {
                    throw new RuntimeException("LesErrorType.DATE_FORMAT_ERROR");
                }
            } else if (DocumentRuleEnums.RuleItemCode.TURNOVER.getCode().equals(po.getRuleItemCode())) {
                String key = RedisKey.generator(LES_MODULE,TYPE, "sequence", documentNameCode);
                long value = sequenceGeneratorService.generator(key,po.getZeroingRuleCode());
                idNumberSb.append(String.format("%0".concat(String.valueOf(po.getFieldLength())).concat("d"), value));
            }
        }
        if (!variableFlag) throw new RuntimeException("LesErrorType.VARIABLE_VALUE_ERROR");
        return idNumberSb.toString();
    }

    /**
     * 根据单据名称编码获取变量列表
     * @param documentNameCode 单据名称编码
     * @return 变量列表
     */
    @Override
    public List<String> getVariableByRuleCode(String documentNameCode) {
        DocumentRulePo rulePo = this.getOne(Wrappers.<DocumentRulePo>lambdaQuery()
                .eq(DocumentRulePo::getDataStatus, DataStatusEnum.NORMAL.getCode())
                .eq(DocumentRulePo::getEnableStatus, EnableEnum.ENABLE.getCode())
                .eq(DocumentRulePo::getDocumentNameCode, documentNameCode));
        if (Objects.isNull(rulePo)) {
            return null;
        }
        List<DocumentRuleItemPo> itemList = documentRuleItemService.listByRuleId(rulePo.getId());
        return itemList.stream().filter(form -> DocumentRuleEnums.RuleItemCode.VARIABLE.getCode().equals(form.getRuleItemCode())).collect(Collectors.toList())
                .stream().map(DocumentRuleItemPo::getRuleValueCode).collect(Collectors.toList());
    }


}