package com.funcas.pboot.module.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 数据字典数据访问
 *
 */
@Mapper
public interface DataDictionaryMapper extends BaseMapper<DataDictionary> {

    IPage<DataDictionary> find(Page<DataDictionary> page, @Param("filter") Map<String, Object> filter);

    /**
     * 判断代码是否唯一
     *
     * @param code 代码
     *
     * @return true 表示唯一，否则 false
     */
    boolean isCodeUnique(@Param("code") String code);
}
