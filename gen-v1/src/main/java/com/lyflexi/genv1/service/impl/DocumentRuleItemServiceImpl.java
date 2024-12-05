package com.lyflexi.genv1.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.lyflexi.genv1.dao.DocumentRuleItemMapper;
import com.lyflexi.genv1.enums.DataStatusEnum;
import com.lyflexi.genv1.model.form.DocumentRuleItemModifyForm;
import com.lyflexi.genv1.model.po.DocumentRuleItemPo;
import com.lyflexi.genv1.model.po.DocumentRulePo;
import com.lyflexi.genv1.service.IDocumentRuleItemService;
import com.lyflexi.genv1.utils.VOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentRuleItemServiceImpl extends ServiceImpl<DocumentRuleItemMapper, DocumentRuleItemPo> implements IDocumentRuleItemService {

    @Override
    public List<DocumentRuleItemPo> listByRuleId(Long ruleId) {
        return this.list(Wrappers.<DocumentRuleItemPo>lambdaQuery().eq(DocumentRuleItemPo::getDocumentRuleId, ruleId)
                .eq(DocumentRuleItemPo::getDataStatus, DataStatusEnum.NORMAL.getCode())
                .orderByAsc(DocumentRuleItemPo::getItemSort));
    }

    /**
     * 规则明细修改流程:
     *   1.删除原来规则
     *   2.新增新的归曾
     * @param rulePo 规则主表数据
     * @param itemForms 明细项目
     */
    @Override
    public boolean batchUpdate(DocumentRulePo rulePo, List<DocumentRuleItemModifyForm> itemForms) {
        log.info("单据规则明细修改，规则信息 {}, 明细条数 {}", rulePo.getId(), itemForms.size());
        List<DocumentRuleItemPo> itemPos = VOUtil.listVo(itemForms, DocumentRuleItemPo.class);
        if (CollectionUtils.isEmpty(itemPos)) {
            return true;
        }
        // 删除原始数据
        this.deleteByRuleIds(Lists.newArrayList(rulePo.getId()));
        // 新增规则明细
        itemPos.forEach(itemPo -> {
            // 新增流程
            itemPo.setDocumentRuleId(rulePo.getId());
            itemPo.setId(null);
            this.save(itemPo);
            log.info("单据规则明细修改，已经新增明细 {}", itemPo.getId());
        });
        return true;
    }

    @Override
    public boolean deleteByRuleIds (List<Long> ruleIds) {
        return this.update(Wrappers.<DocumentRuleItemPo>lambdaUpdate()
                .in(DocumentRuleItemPo::getDocumentRuleId, ruleIds)
                .set(DocumentRuleItemPo::getDataStatus, DataStatusEnum.DELETE.getCode()));
    }
}