package com.funcas.pboot.module.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.module.upms.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组数据访问
 * 
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {


    /**
     * 获取用户所在的组
     *
     * @param userId 用户主键 ID
     *
     * @return 组实体 Map 集合
     */
    List<Group> getUserGroups(@Param("userId") Long userId);


    /**
     * 删除组与资源的关联
     *
     * @param id 组主键 ID
     */
    void deleteResourceAssociation(@Param("id") Long id);

    /**
     * 删除组与用户的关联
     *
     * @param id 组主键 ID
     */
    void deleteUserAssociation(@Param("id") Long id);


    /**
     * 新增组与资源的关联
     *
     * @param id 组主键 ID
     * @param resourceIds 关联的组主键 ID 集合
     */
    void insertResourceAssociation(@Param("id") Long id, @Param("resourceIds") List<Long> resourceIds);

    /**
     * 新增组与用户的关联
     *
     * @param id 组主键 ID
     * @param userIds 关联的组主键 ID 集合
     */
    void insertUserAssociation(@Param("id") Long id, @Param("userIds") List<Long> userIds);


    /**
     * 查询组
     *
     * @param filter 查询条件
     *
     * @return 组实体 Map集合
     */
    IPage<Group> find(Page<Group> page, @Param("filter") Map<String, Object> filter);

    /**
     * 判断名称是否唯一
     *
     * @param name 名称
     *
     * @return true 表示唯一，否则 false
     */
    boolean isNameUnique(@Param("name") String name);

    List<Long> getUserGroupListByUserId(@Param("id") Long id);
}
