package com.funcas.pboot.module.upms.service;

import com.funcas.pboot.module.upms.entity.Unit;

import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月26日
 */
public interface IUnitService {

    public void saveUnit(Unit entity);

    public List<Unit> getUnitByParentId(Long pid);

    public List<Unit> getAllUnits();

    public void deleteUnit(Long id);

    public Unit selectOne(Long id);

    public List<Unit> getGroupUnit(Long id);

    public List<String> getCheckedUnitsByGroupId(Long groupId);

    public List<Unit> getUnitByDataScope(Long groupId);

}
