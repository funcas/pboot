package com.funcas.pboot.module.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.upms.entity.DataDictionary;
import com.funcas.pboot.module.upms.entity.DictionaryCategory;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月15日
 */
public interface ISystemVariableService {

    /**
     * 获取数据字典
     *
     * @param id 数据字典主键 ID
     *
     * @return 数据字典 Map 实体
     */
    public DataDictionary getDataDictionary(Long id);

    /**
     * 获取数据字典
     *
     * @param code 数据字典代码
     *
     * @return 数据字典 Map 实体
     */
    public DataDictionary getDataDictionary(String code);

    /**
     * 获取数据字典
     *
     * @param code 字典类别代码
     *
     * @return 数据字典 Map 实体集合
     */
    public List<DataDictionary> getDataDictionaries(String code);

    /**
     * 删除数据字典
     *
     * @param ids 数据字典主键 ID
     */
    public void deleteDataDictionaries(List<Long> ids);

    /**
     * 保存数据字典
     *
     * @param entity 数据字典 Map 实体
     */
    public void saveDataDictionary(DataDictionary entity);

    /**
     * 判断数据字典代码是否唯一
     *
     * @param code 数据字典代码
     *
     * @return ture 表示唯一，否则 false。
     */
    public boolean isDataDictionaryCodeUnique(String code);

    /**
     * 查询数据字典
     *
     * @param filter 查询条件
     *
     * @return 数据字典 Map 实体集合
     */
    public List<DataDictionary> findDataDictionaries(Map<String, Object> filter);

    /**
     * 查询数据字典
     *
     * @param pageRequest 分页请求参数
     * @param filter      查询条件
     *
     * @return 数据字典实体 Map 的分页对象
     */
//    public Page<DataDictionary> findDataDictionaries(PageRequest pageRequest, Map<String, Object> filter);

    //----------------------------------- 字典类别管理 ----------------------------------------//

    /**
     * 获取字典类别
     *
     * @param id 字典类别主键ID
     *
     * @return 字典类别 Map 实体
     */
    public DictionaryCategory getDictionaryCategory(Long id);

    /**
     * 获取所有字典类别
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 字典类别 Map 实体集合
     */
    public List<DictionaryCategory> getAllDictionaryCategories(Long... ignore);

    /**
     * 保存字典类别
     *
     * @param entity 字典类别 Map 实体
     */
    public void saveDictionaryCategory(DictionaryCategory entity);

    /**
     * 判断字典类别代码是否唯一
     *
     * @param code 字典类别代码
     *
     * @return ture 表示唯一，否则 false。
     */
    public boolean isDictionaryCategoryCodeUnique(String code);

    /**
     * 删除字典类别
     *
     * @param ids 字典类别主键 ID 集合
     */
    public void deleteDictionaryCategories(List<Long> ids) ;

    /**
     * 查询字典类别
     *
     * @param pageRequest 分页请求参数
     * @param filter      查询条件
     *
     * @return 字典类别实体 Map 的分页对象
     */
    public IPage<DictionaryCategory> findDictionaryCategories(PageRequest pageRequest, Map<String, Object> filter);
}
