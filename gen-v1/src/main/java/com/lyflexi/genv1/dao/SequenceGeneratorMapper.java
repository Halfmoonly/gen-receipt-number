package com.lyflexi.genv1.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyflexi.genv1.model.po.SequenceGeneratorPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SequenceGeneratorMapper
 * @Description: 在此添加类描述
 * @Author: ma.honggang
 * @Version: v1.0
 * @Date: 2022/10/8
 */
@Mapper
@Repository
public interface SequenceGeneratorMapper extends BaseMapper<SequenceGeneratorPo> {
}
