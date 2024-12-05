package com.lyflexi.genv1.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyflexi.genv1.enums.EnableEnum;
import com.lyflexi.genv1.enums.EnableForm;
import com.lyflexi.genv1.model.form.DocumentRuleAddForm;
import com.lyflexi.genv1.model.form.DocumentRuleModifyForm;
import com.lyflexi.genv1.model.form.VariableForm;
import com.lyflexi.genv1.model.param.DocumentRuleQueryParam;
import com.lyflexi.genv1.model.po.DocumentRulePo;
import com.lyflexi.genv1.model.vo.DocumentRuleVo;


import java.util.List;

public interface IDocumentRuleService extends IService<DocumentRulePo> {



    /**
     * 删除规则
     * @param ids 规则ID集合
     */
    boolean delete (List<Long> ids);



    /**
     * 批量启用/禁用/删除
     * @param ids 规则ID列表
     * @param enable 状态值
     */
    boolean enable (List<Long> ids, EnableEnum enable);


    /**
     * 根据单据规则生成单据号
     * @param documentNameCode
     * @param list
     * @return
     */
    String generateIdNumber (String documentNameCode, List<VariableForm> list);

    /**
     * 根据单据名称编码获取变量列表
     * @param documentNameCode
     * @return
     */
    List<String> getVariableByRuleCode (String documentNameCode);
}