package com.funcas.pboot.module.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funcas.pboot.module.upms.entity.Unit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月21日
 */
@Mapper
public interface UnitMapper extends BaseMapper<Unit> {

    List<Unit> findOrgByParentId(@Param("parentId") Long parentId);

    List<Unit> findTwoLevelOrgByParentId(@Param("rootId") Long rootId);

    String genOrgCode(@Param("parentId") Long parentId);

    List<Unit> selectGroupUnit(@Param("groupId") Long groupId);

    public List<String> getCheckedUnitsByGroupId(@Param("groupId") Long groupId);

    public List<Unit> getUnits(@Param("filter") Map<String, Object> filter);
}
