package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.common.enumeration.entity.DelFlag;
import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.module.ServiceTestCaseSupport;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.service.IUnitService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class UnitServiceImplTest extends ServiceTestCaseSupport {

    private static final String UNIT_TABLE_NAME = "tb_unit";

    @Autowired
    private IUnitService unitService;

    private Unit generateUnit() {
        Unit unit = new Unit();
        unit.setName("test");
        unit.setParentId(1L);
        unit.setSort(1);
        unit.setState(State.ENABLE.getValue());
        return unit;
    }

    @Test
    @Transactional
    public void saveUnit() {
        Unit unit = this.generateUnit();
        int before = countRowsInTable(UNIT_TABLE_NAME);
        unitService.saveUnit(unit);
        int after = countRowsInTable(UNIT_TABLE_NAME);
        Assert.assertEquals(before + 1, after);
    }

    @Test
    @Transactional
    public void getUnitByParentId() {
        List<Unit> before = unitService.getUnitByParentId(1L);
        Unit unit = this.generateUnit();
        unitService.saveUnit(unit);
        List<Unit> after = unitService.getUnitByParentId(1L);
        Assert.assertEquals(before.size() + 1, after.size());
    }


    @Test
    @Transactional
    public void deleteUnit() {
        Unit unit = this.generateUnit();
        unitService.saveUnit(unit);
        unitService.deleteUnit(unit.getId());
        Unit entity = unitService.selectOne(unit.getId());
        Assert.assertEquals(entity.getDelFlag(), DelFlag.DELETED.getValue());
    }

    @Test
    @Transactional
    public void selectOne() {
        Unit unit = this.generateUnit();
        unitService.saveUnit(unit);
        Unit entity = unitService.selectOne(unit.getId());
        Assert.assertEquals(unit.getName(), entity.getName());
    }

    @Test
    public void getGroupUnit() {
    }

    @Test
    public void getCheckedUnitsByGroupId() {
    }
}