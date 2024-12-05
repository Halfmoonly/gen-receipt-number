package com.lyflexi.genv1.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lyflexi.genv1.model.form.DocumentRuleItemModifyForm;
import com.lyflexi.genv1.model.po.DocumentRuleItemPo;
import com.lyflexi.genv1.model.po.DocumentRulePo;

import java.util.List;

public interface IDocumentRuleItemService extends IService<DocumentRuleItemPo> {

    /**
     * 根据单据规则ID查询规则明细
     * @param ruleId 规则ID
     * @return List<DocumentRuleItemPo>
     */
    List<DocumentRuleItemPo> listByRuleId(Long ruleId);

    /**
     * 批量更新规则明细
     * @param rulePo 规则主表数据
     * @param itemForms 明细项目
     */
    boolean batchUpdate (DocumentRulePo rulePo, List<DocumentRuleItemModifyForm> itemForms);

    /**
     * 根据单据规则ID删除规则明细
     * @param ruleIds 规则ID集合
     */
    boolean deleteByRuleIds (List<Long> ruleIds);
}