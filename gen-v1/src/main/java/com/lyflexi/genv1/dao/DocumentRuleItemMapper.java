package com.lyflexi.genv1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyflexi.genv1.model.po.DocumentRuleItemPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* Created by Mybatis Generator on 2021/12/13
*/
@Mapper
@Repository
public interface DocumentRuleItemMapper extends BaseMapper<DocumentRuleItemPo> {
}