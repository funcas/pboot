package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.common.enumeration.FieldType;
import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import com.funcas.pboot.module.util.VariableUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 数据字典管理
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@RestController
@RequestMapping("/sys")
public class DataDictionaryController extends BaseController {

    private final ISystemVariableService systemVariableService;

    @Autowired
    public DataDictionaryController(ISystemVariableService systemVariableService) {
        this.systemVariableService = systemVariableService;
    }

    /**
     * 分页查询所有数据字典列表
     * @param pageRequest
     * @param filter
     * @return
     */
    @GetMapping("/dicts")
    @PreAuthorize("hasAuthority('data-dictionary:list')")
    public Object getDicts(PageRequest pageRequest, Map<String,Object> filter){
        return success(systemVariableService.findDataDictionaries(pageRequest, filter));
    }

    @GetMapping("/field-types")
    public Object getFieldTypes() {
        return success(VariableUtils.get(FieldType.class));
    }

    /**
     * 保存数据字典
     * @param entity
     * @return
     */
    @PostMapping("/dict")
    @PreAuthorize("hasAuthority('data-dictionary:save')")
    public Object saveDict(@RequestBody DataDictionary entity){
        systemVariableService.saveDataDictionary(entity);
        return success(entity);
    }

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    @DeleteMapping("/dict/{id}")
    @PreAuthorize("hasAuthority('data-dictionary:delete')")
    public Object delDict(@PathVariable("id") Long id){
        systemVariableService.deleteDataDictionaries(Lists.newArrayList(id));
        return success(id);
    }

}
