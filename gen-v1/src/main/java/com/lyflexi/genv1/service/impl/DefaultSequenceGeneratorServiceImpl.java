package com.lyflexi.genv1.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.lyflexi.genv1.dao.SequenceGeneratorMapper;
import com.lyflexi.genv1.enums.DataStatusEnum;
import com.lyflexi.genv1.enums.DocumentRuleEnums;
import com.lyflexi.genv1.model.po.SequenceGeneratorPo;
import com.lyflexi.genv1.service.ISequenceGeneratorService;
import com.lyflexi.genv1.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁防止跳号
 */

@Service
public class DefaultSequenceGeneratorServiceImpl implements ISequenceGeneratorService<String, Long> {

    @Autowired
    private SequenceGeneratorMapper sequenceGeneratorMapper;

    private Integer DEFAULT_TIMES = 30;

    private Long DEFAULT_VERSION = 0L;

    private Long DEFAULT_START = 1L;

    private Long DEFAULT_STEP = 1L;

    @Override
    public Long execute(String key,String rate) {
        Long rs = null;
        AtomicInteger index = new AtomicInteger(0);
        while (index.incrementAndGet() <= DEFAULT_TIMES) {
            Long time = time(rate);
            SequenceGeneratorPo sequenceGeneratorPo = sequenceGeneratorMapper.selectOne(Wrappers.<SequenceGeneratorPo>lambdaQuery().eq(SequenceGeneratorPo::getKey, key).ge(!Objects.isNull(time),SequenceGeneratorPo::getExpire, System.currentTimeMillis()).orderByDesc(SequenceGeneratorPo::getAddTime).last(" limit 1 "));
            if (Objects.isNull(sequenceGeneratorPo)) {
                sequenceGeneratorPo = SequenceGeneratorPo.builder().key(key).value(DEFAULT_START).version(DEFAULT_VERSION).build();
                sequenceGeneratorPo.setExpire(time);
                sequenceGeneratorPo.setDataStatus(DataStatusEnum.NORMAL.getCode());
                try {
                    sequenceGeneratorMapper.insert(sequenceGeneratorPo);
                    rs = DEFAULT_START;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Long id = sequenceGeneratorPo.getId();
                Long version = sequenceGeneratorPo.getVersion();
                Long trs = sequenceGeneratorPo.getValue() + DEFAULT_STEP;
                int num = sequenceGeneratorMapper.update(null,Wrappers.<SequenceGeneratorPo>lambdaUpdate().set(SequenceGeneratorPo::getValue,trs).setSql(" version = version + 1 ").eq(SequenceGeneratorPo::getId,id).eq(SequenceGeneratorPo::getKey, key).eq(SequenceGeneratorPo::getVersion,version));
                if(num > 0){
                    rs = trs;
                    break;
                }
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(Objects.isNull(rs)){
            throw new RuntimeException("获取序列-DB方式异常");
        }
        return rs;
    }

    private Long time(String rate){
        Long rs = null;
        if(StringUtils.isEmpty(rate)){
            return rs;
        }
        Date date = new Date();
        if (DocumentRuleEnums.TurnoverMode.DAY.getCode().equals(rate)){
            rs = DateUtil.geNextDay(date).getTime();
        } else if (DocumentRuleEnums.TurnoverMode.WEEK.getCode().equals(rate)){
            rs = DateUtil.getNextWeek(date).getTime();
        } else if (DocumentRuleEnums.TurnoverMode.MONTH.getCode().equals(rate)){
            rs = DateUtil.getNextMonth(date).getTime();
        } else if (DocumentRuleEnums.TurnoverMode.YEAR.getCode().equals(rate)){
            rs = DateUtil.getNextYear(date).getTime();
        } else{
            throw new RuntimeException("未知类型的清零过期频率");
        }
        return rs;
    }

    @Override
    public Long query(String key, String rate) {
        Long time = time(rate);
        SequenceGeneratorPo sequenceGeneratorPo = sequenceGeneratorMapper.selectOne(Wrappers.<SequenceGeneratorPo>lambdaQuery().eq(SequenceGeneratorPo::getKey, key).ge(!Objects.isNull(time), SequenceGeneratorPo::getExpire, System.currentTimeMillis()).orderByDesc(SequenceGeneratorPo::getAddTime).last(" limit 1 "));
        if (Objects.isNull(sequenceGeneratorPo)) {
            return 0L;
        } else {
            return sequenceGeneratorPo.getValue();
        }
    }

}
