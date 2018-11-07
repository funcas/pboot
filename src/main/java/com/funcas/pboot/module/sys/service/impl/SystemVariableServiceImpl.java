package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.exception.ServiceException;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.sys.entity.DictionaryCategory;
import com.funcas.pboot.module.sys.mapper.DataDictionaryMapper;
import com.funcas.pboot.module.sys.mapper.DictionaryCategoryMapper;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统变量业务逻辑
 *
 */
@Service("systemVariableService")
@Transactional(rollbackFor = Exception.class)
public class SystemVariableServiceImpl implements ISystemVariableService {

    private final DataDictionaryMapper dataDictionaryMapper;

    private final DictionaryCategoryMapper dictionaryCategoryMapper;

    @Autowired
    public SystemVariableServiceImpl(DataDictionaryMapper dataDictionaryMapper, DictionaryCategoryMapper dictionaryCategoryMapper) {
        this.dataDictionaryMapper = dataDictionaryMapper;
        this.dictionaryCategoryMapper = dictionaryCategoryMapper;
    }

    //----------------------------------- 数据字典管理 ----------------------------------------//

    /**
     * 获取数据字典
     *
     * @param id 数据字典主键 ID
     *
     * @return 数据字典 Map 实体
     */
    @Override
    public DataDictionary getDataDictionary(Long id) {
        return dataDictionaryMapper.selectById(id);
    }

    /**
     * 获取数据字典
     *
     * @param code 数据字典代码
     *
     * @return 数据字典 Map 实体
     */
    @Override
    @Cacheable(value = "dataDictionaryCache", key = "#code")
    public DataDictionary getDataDictionary(String code) {
        return dataDictionaryMapper.selectOne(new QueryWrapper<DataDictionary>().eq("code", code));
    }

    /**
     * 获取数据字典
     *
     * @param code 字典类别代码
     *
     * @return 数据字典 Map 实体集合
     */
    @Override
    @Cacheable(value = "dataDictionaryCache", key = "#code")
    public List<DataDictionary> getDataDictionaries(String code) {
        return dataDictionaryMapper
                .selectList(new QueryWrapper<DataDictionary>().eq("fk_category_id", code));
    }

    /**
     * 删除数据字典
     *
     * @param ids 数据字典主键 ID
     */
    @Override
    @CacheEvict(value = "dataDictionaryCache", allEntries = true)
    public void deleteDataDictionaries(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        for (Long id : ids) {
            dataDictionaryMapper.deleteById(id);
        }
    }

    /**
     * 保存数据字典
     *
     * @param entity 数据字典 Map 实体
     */
    @Override
    @CacheEvict(value = "dataDictionaryCache", allEntries = true)
    public void saveDataDictionary(DataDictionary entity) {
        if (entity.getId() != null) {
            dataDictionaryMapper.updateById(entity);
        } else {
            String code = entity.getCode();

            if (!isDataDictionaryCodeUnique(code)) {
                throw new ServiceException("数据字典代码[" + code + "]已存在");
            }

            Long parentId = entity.getFkCategoryId();
            DictionaryCategory parent = getDictionaryCategory(parentId);
            String parentCode = parent.getCode();
            entity.setCode(parentCode + "_" + code);
            dataDictionaryMapper.insert(entity);
        }
    }

    /**
     * 判断数据字典代码是否唯一
     *
     * @param code 数据字典代码
     *
     * @return ture 表示唯一，否则 false。
     */
    @Override
    public boolean isDataDictionaryCodeUnique(String code) {
        return !dataDictionaryMapper.isCodeUnique(code);
    }

    /**
     * 查询数据字典
     *
     * @param filter 查询条件
     *
     * @return 数据字典 Map 实体集合
     */
    @Override
    public List<DataDictionary> findDataDictionaries(Map<String, Object> filter) {
        return dataDictionaryMapper.selectByMap(filter);
    }

    /**
     * 查询数据字典
     *
     * @param pageRequest 分页请求参数
     * @param filter      查询条件
     *
     * @return 数据字典实体 Map 的分页对象
     */
    @Override
    public IPage<DataDictionary> findDataDictionaries(PageRequest pageRequest, Map<String, Object> filter) {
        return dataDictionaryMapper.find(new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize()), filter);
    }

    //----------------------------------- 字典类别管理 ----------------------------------------//

    /**
     * 获取字典类别
     *
     * @param id 字典类别主键ID
     *
     * @return 字典类别 Map 实体
     */
    @Override
    public DictionaryCategory getDictionaryCategory(Long id) {
        return dictionaryCategoryMapper.selectById(id);
    }

    /**
     * 获取所有字典类别
     *
     * @param ignore 忽略的 ID 值
     *
     * @return 字典类别 Map 实体集合
     */
    @Override
    public List<DictionaryCategory> getAllDictionaryCategories(Long... ignore) {
        return dictionaryCategoryMapper.getAll(ignore);
    }

    /**
     * 保存字典类别
     *
     * @param entity 字典类别 Map 实体
     */
    @Override
    public void saveDictionaryCategory(DictionaryCategory entity) {
        if (entity.getId() != null) {
            dictionaryCategoryMapper.updateById(entity);
        } else {
            String code = entity.getCode();
            if (!isDictionaryCategoryCodeUnique(code)) {
                throw new ServiceException("字典类别代码[" + code + "]已存在");
            }
            dictionaryCategoryMapper.insert(entity);
        }
    }

    /**
     * 判断字典类别代码是否唯一
     *
     * @param code 字典类别代码
     *
     * @return ture 表示唯一，否则 false。
     */
    @Override
    public boolean isDictionaryCategoryCodeUnique(String code) {
        return !dictionaryCategoryMapper.isCodeUnique(code);
    }

    /**
     * 删除字典类别
     *
     * @param ids 字典类别主键 ID 集合
     */
    @Override
    @CacheEvict(value = "dataDictionaryCache", allEntries = true)
    public void deleteDictionaryCategories(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        for (Long id : ids) {
            dictionaryCategoryMapper.deleteDataDictionaryAssociation(id);
            dictionaryCategoryMapper.deleteById(id);
        }
    }


    /**
     * 查询字典类别
     *
     * @param pageRequest 分页请求参数
     * @param filter      查询条件
     *
     * @return 字典类别实体 Map 的分页对象
     */
    @Override
    public IPage<DictionaryCategory> findDictionaryCategories(PageRequest pageRequest, Map<String, Object> filter) {
        return dictionaryCategoryMapper.find(new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize()), filter);
    }

}
