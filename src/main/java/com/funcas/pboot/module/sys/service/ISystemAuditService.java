package com.funcas.pboot.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.sys.entity.OperatingRecord;
import com.funcas.pboot.module.sys.mapper.OperatingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月06日
 */
public interface ISystemAuditService {

    public void saveOperatingRecord(OperatingRecord entity);

    public IPage<OperatingRecord> findOperatingRecordPaged(PageRequest pageRequest, Map<String,Object> filter);
}
