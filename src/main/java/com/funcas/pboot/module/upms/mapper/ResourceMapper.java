package com.funcas.pboot.module.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funcas.pboot.module.upms.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源数据访问
 *
 * @author funcas
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {


    /**
     * 获取所有资源
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 资源实体集合
     */
    List<Resource> getAll(@Param("ignore") Long... ignore);

    /**
     * 获取相关联的子资源
     *
     * @param id 资源主键 ID
     *
     * @return 资源实体 Map 集合
     */
    List<Resource> getChildren(@Param("id") Long id);

    /**
     * 获取用户资源
     *
     * @param userId 用户主键 ID
     *
     * @return 资源实体 Map 集合
     */
    List<Resource> getUserResources(@Param("userId") Long userId);

    /**
     * 获取组资源
     *
     * @param groupId 组主键 ID
     *
     * @return 资源实体 Map 集合
     */
    List<Resource> getGroupResources(@Param("groupId") Long groupId);

    /**
     * 删除资源与组的关联
     *
     * @param id 资源主键 ID
     */
    void deleteGroupAssociation(@Param("id") Long id);

    List<Resource> selectGroupResources(@Param("groupId") Long groupId);

    List<String> getResourceByGroupIds(@Param("groupId") Long groupId);
}
