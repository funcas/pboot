package com.funcas.pboot.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.sys.entity.DictionaryCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典类别数据访问
 *
 */
@Mapper
public interface DictionaryCategoryMapper extends BaseMapper<DictionaryCategory> {

    /**
     * 获取所有字典类别
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 字典类别 Map 实体集合
     */
    List<DictionaryCategory> getAll(@Param("ignore") Long... ignore);

    /**
     * 判断代码是否唯一
     *
     * @param code 代码
     *
     * @return true 表示唯一，否则 false
     */
    boolean isCodeUnique(@Param("code") String code);

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
    IPage<DictionaryCategory> find(Page<DataDictionary> page, @Param("filter") Map<String, Object> filter);
}
