package com.funcas.pboot.module.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * 用户数据访问
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    /**
     * 获取用户
     *
     * @param username 用户登录帐号或电子实体
     *
     * @return 用户实体 Map
     */
    User getByUsername(@Param("username") String username);


    List<User> findUsersByNickname(@Param("nickname") String nickname);


    /**
     * 删除用户与组的关联
     *
     * @param id 用户主键 ID
     */
    void deleteGroupAssociation(@Param("id") Long id);


    /**
     * 更新密码
     *
     * @param id 用户主键 ID
     * @param password 密码
     */
    void updatePassword(@Param("id") Long id, @Param("password") String password);


    /**
     * 新增用户与组的关联
     *
     * @param id 用户主键 ID
     * @param groups
     */
    void insertGroupAssociation(@Param("id") Long id, @Param("groups") List<Group> groups);


    /**
     * 查询用户
     *
     * @param filter 查询条件
     *
     * @return 用户实体 Map集合
     */
    IPage<User> find(Page<User> page, @Param("filter") Map<String, Object> filter);

    List<User> findUserByOrgId(@Param("orgId") Long orgId);

    List<String> getUserPerms(@Param("id") Long uid);

}
