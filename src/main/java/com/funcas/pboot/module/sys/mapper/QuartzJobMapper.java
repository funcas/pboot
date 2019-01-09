package com.funcas.pboot.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funcas.pboot.module.sys.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

//    IPage<QuartzJob> findQuartzJobPaged(Page<QuartzJob> page, Map<String,Object> filter);
}
