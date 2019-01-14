package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.ServiceTestCaseSupport;
import com.funcas.pboot.module.sys.entity.OperatingRecord;
import com.funcas.pboot.module.sys.service.ISystemAuditService;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class SystemAuditServiceImplTest extends ServiceTestCaseSupport {

    private static final String TABLE_NAME = "tb_operating_record";

    @Autowired
    private ISystemAuditService auditService;

    @Test
    @Transactional
    public void saveOperatingRecord() {
        int before = countRowsInTable(TABLE_NAME);
        OperatingRecord operatingRecord = new OperatingRecord();
        operatingRecord.setUsername("test");
        operatingRecord.setStartDate(new Date());
        operatingRecord.setEndDate(new Date());
        operatingRecord.setIp("0.0.0.0");
        operatingRecord.setMethod("func");
        auditService.saveOperatingRecord(operatingRecord);
        int after = countRowsInTable(TABLE_NAME);
        Assert.assertEquals(before + 1, after);

    }

    @Test
    public void findOperatingRecordPaged() {
        int total = countRowsInTable(TABLE_NAME);
        IPage<OperatingRecord> page = auditService.findOperatingRecordPaged(new PageRequest(1,10), Maps.newHashMap());
        Assert.assertEquals(total, page.getTotal());
    }
}