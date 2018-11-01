package com.funcas.pboot.module.upms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.module.upms.entity.DictionaryCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典类别数据访问
 *
 */
@Mapper
public interface DictionaryCategoryMapper {

    /**
     * 获取字典类别
     *
     * @param id 字典类别主键ID
     *
     * @return 字典类别 Map 实体
     */
    DictionaryCategory get(@Param("id") Long id);

    /**
     * 获取所有字典类别
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 字典类别 Map 实体集合
     */
    List<DictionaryCategory> getAll(@Param("ignore") Long... ignore);

    /**
     * 新增字典类别
     *
     * @param entity 字典类别 Map 实体
     */
    void insert(@Param("entity") DictionaryCategory entity);

    /**
     * 更新字典类别
     *
     * @param entity 字典类别 Map 实体
     */
    void update(@Param("entity") DictionaryCategory entity);

    /**
     * 删除字典类别
     *
     * @param id 字典类别主键 ID
     */
    void delete(@Param("id") Long id);

    /**
     * 删除字典类别关联的数据字典
     *
     * @param id 字典类别主键 ID
     */
    void deleteDataDictionaryAssociation(@Param("id") Long id);

    /**
     * 查询字典类别
     *
     * @param filter 查询条件
     *
     * @return 字典类别实体 Map 集合
     */
    IPage<DictionaryCategory> find(Page<DictionaryCategory> page, @Param("filter") Map<String, Object> filter);

    /**
     * 统计字典类别
     *
     * @param filter 查询条件
     *
     * @return 统计字典类别总数
     */
    long count(@Param("filter") Map<String, Object> filter);

    /**
     * 判断代码是否唯一
     *
     * @param code 代码
     *
     * @return true 表示唯一，否则 false
     */
    boolean isCodeUnique(@Param("code") String code);
}
