package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.sys.entity.OperatingRecord;
import com.funcas.pboot.module.sys.mapper.OperatingRecordMapper;
import com.funcas.pboot.module.sys.service.ISystemAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月06日
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SystemAuditServiceImpl implements ISystemAuditService {

    @Autowired
    private OperatingRecordMapper operatingRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperatingRecord(OperatingRecord entity) {
        operatingRecordMapper.insert(entity);
    }

    @Override
    public IPage<OperatingRecord> findOperatingRecordPaged(PageRequest pageRequest, Map<String, Object> filter) {
        QueryWrapper<OperatingRecord> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("start_date");
        return operatingRecordMapper.selectPage(new Page<>(pageRequest.getPageNumber(),
                pageRequest.getPageSize()), wrapper);
    }
}
