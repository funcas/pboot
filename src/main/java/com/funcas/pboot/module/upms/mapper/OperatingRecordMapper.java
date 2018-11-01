package com.funcas.pboot.module.upms.mapper;

import com.funcas.pboot.module.upms.entity.OperatingRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月26日
 */
@Mapper
public interface OperatingRecordMapper  {

    List<OperatingRecord> findRecordPaged(Map<String, Object> filters);
}
