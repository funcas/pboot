package com.funcas.pboot.module.upms.mapper;

import com.funcas.pboot.module.upms.entity.DataDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据字典数据访问
 *
 */
@Mapper
public interface DataDictionaryMapper {

    /**
     * 获取数据字典
     *
     * @param id 数据字典主键 ID
     *
     * @return 数据字典实体 Map
     */
    DataDictionary get(@Param("id") Long id);

    /**
     * 获取数据字典
     *
     * @param code 字典类别代码
     *
     * @return 数据字典实体 Map
     */
    List<DataDictionary> getByCategoryCode(@Param("code") String code);

    /**
     * 获取数据字典
     *
     * @param code 字典代码
     *
     * @return 数据字典实体 Map
     */
    DataDictionary getByCode(String code);

    /**
     * 新增数据字典
     *
     * @param entity 数据字典实体 Map
     */
    void insert(@Param("entity") DataDictionary entity);

    /**
     * 更新数据字典
     *
     * @param entity 数据字典实体 Map
     */
    void update(@Param("entity") DataDictionary entity);

    /**
     * 删除数据字典
     *
     * @param id 数据字典主键 ID
     */
    void delete(@Param("id") Long id);

    /**
     * 判断代码是否唯一
     *
     * @param code 代码
     *
     * @return true 表示唯一，否则 false
     */
    boolean isCodeUnique(@Param("code") String code);

    /**
     * 查询数据字典
     *
     * @param filter 查询条件
     *
     * @return 数据字典实体 Map 集合
     */
    List<DataDictionary> find(@Param("filter") Map<String, Object> filter);

    /**
     * 统计数据字典
     *
     * @param filter 查询条件
     *
     * @return 统计数据字典总数
     */
    long count(@Param("filter") Map<String, Object> filter);
}
