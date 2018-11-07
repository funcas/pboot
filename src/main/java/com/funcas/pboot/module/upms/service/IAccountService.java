package com.funcas.pboot.module.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.enumeration.entity.ResourceType;
import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.entity.Resource;
import com.funcas.pboot.module.upms.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月11日
 */
public interface IAccountService {

    public User getUser(Long id);

    public void saveUser(User entity);

    public void updateUserPassword(User entity, String oldPassword, String newPassword);

    public User getUserByUsername(String username);

    public boolean isUsernameUnique(String username);

    public boolean isUserEmailUnique(String email);

    public IPage<User> findUsers(PageRequest pageRequest, Map<String, Object> filter);

    public List<User> findUsersByNickname(String nickname);

    public void deleteUser(User entity);
    public void deleteUsers(List<Long> ids);

    public List<User> findUsersByOrgId(Long orgId);

    /**
     * 获取组
     *
     * @param id 组主键 ID
     *
     * @return 组实体 Map
     */
    public Group getGroup(Long id);

    /**
     * 获取用户所在的组
     *
     * @param userId 用户主键 ID
     *
     * @return 组实体 Map 集合
     */
    public List<Group> getUserGroups(Long userId);

    /**
     * 删除组
     *
     * @param ids 组主键 ID 集合
     */
    public void deleteGroups(List<Long> ids);

    /**
     * 保存组
     *
     * @param entity      组实体 Map
     */
    public void saveGroup(Group entity);

    /**
     * 判断组名称是否存在
     *
     * @param name 组名称
     *
     * @return ture 表示存在，否则 false。
     */
    public boolean isGroupNameUnique(String name);

    /**
     * 查询组
     *
     * @param filter 查询条件
     *
     * @return 组实体 Map 集合
     */
    public IPage<Group> findGroups(PageRequest pageRequest, Map<String, Object> filter);


    //----------------------------------- 资源管理 ----------------------------------------//

    /**
     * 获取资源
     *
     * @param id 资源主键 ID
     *
     * @return 资源实体 Map
     */
    public Resource getResource(Long id);

    /**
     * 获取所有资源
     *
     * @return 资源实体 Map 集合
     */
    public List<Resource> getResources(Long... ignore) ;

    /**
     * 获取用户资源
     *
     * @param userId 用户主键 ID
     *
     * @return 资源实体 Map 集合
     */
    public List<Resource> getUserResources(Long userId) ;
    /**
     * 获取组资源
     *
     * @param groupId 组主键 ID
     *
     * @return 资源实体 Map 集合
     */
    public List<Resource> getGroupResources(Long groupId);

    /**
     * 删除资源
     *
     * @param ids 资源主键 ID 集合
     */
    public void deleteResources(List<Long> ids);

    /**
     * 保存资源
     *
     * @param entity 资源实体 Map
     */
    public void saveResource(Resource entity) ;

    /**
     * 合并资源，要获取资源的父类通过 "parent" key 来获取，如果不存在 "parent" key 表示该资源 Map 为根节点，
     * 要获取资源的子类通过 "children" key 来获取
     *
     * @param resources 要合并的资源实体 Map 集合
     *
     * @return 合并好的树形资源实体 Map 集合
     */
    public List<Resource> mergeResources(List<Resource> resources) ;

    /**
     * 合并资源，要获取资源的父类通过 "parent" key 来获取，如果不存在 "parent" key 表示该资源 Map 为根节点，
     * 要获取资源的子类通过 "children" key 来获取
     *
     * @param resources  要合并的资源实体 Map 集合
     * @param ignoreType 要忽略资源类型
     *
     * @return 合并好的树形资源实体 Map 集合
     */
    public List<Resource> mergeResources(List<Resource> resources, ResourceType ignoreType) ;

    public List<Resource> getResourcesByGroupId(Long groupId);
}
