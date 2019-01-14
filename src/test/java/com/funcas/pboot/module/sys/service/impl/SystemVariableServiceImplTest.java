package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.ServiceTestCaseSupport;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.sys.entity.DictionaryCategory;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class SystemVariableServiceImplTest extends ServiceTestCaseSupport {

    public static final String TABLE_NAME_DICT = "tb_data_dictionary";
    public static final String TABLE_NAME_CATE = "tb_dictionary_category";

    @Autowired
    private ISystemVariableService systemVariableService;

    @Test
    @Transactional
    public void saveDataDictionary() {
        int before = countRowsInTable(TABLE_NAME_DICT);
        DataDictionary dict = new DataDictionary();
        dict.setName("test");
        dict.setFkCategoryId(1L);
        dict.setValue("test");
        dict.setType("I");
        dict.setCode("TEST");
        systemVariableService.saveDataDictionary(dict);
        int after = countRowsInTable(TABLE_NAME_DICT);
        Assert.assertEquals(before + 1, after);

    }

    @Test
    public void findDataDictionaries() {
        int total = countRowsInTable(TABLE_NAME_DICT);
        IPage<DataDictionary> dataDictionaryIPage = systemVariableService.findDataDictionaries(new PageRequest(1, 10), Maps.newHashMap());
        Assert.assertEquals(total, dataDictionaryIPage.getTotal());
    }

    @Test
    @Transactional
    public void saveDictionaryCategory() {
        int before = countRowsInTable(TABLE_NAME_CATE);
        DictionaryCategory category = new DictionaryCategory();
        category.setCode("TEST1");
        category.setName("test");
        category.setEditAble(true);
        category.setRemoveAble(true);
        systemVariableService.saveDictionaryCategory(category);
        int after = countRowsInTable(TABLE_NAME_CATE);
        Assert.assertEquals(before + 1, after);
    }

    @Test
    @Transactional
    public void deleteDictionaryCategories() {
    }

    @Test
    public void findDictionaryCategories() {
        IPage<DictionaryCategory> categoryIPage = systemVariableService.findDictionaryCategories(new PageRequest(1, 10), Maps.newHashMap());
        int total = countRowsInTable(TABLE_NAME_CATE);
        Assert.assertEquals(total, categoryIPage.getTotal());
    }
}