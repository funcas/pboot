package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.PropertyFilters;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.sys.entity.DictionaryCategory;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 字典类别管理
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@RestController
@RequestMapping("/sys")
public class DictionaryCategoryController extends BaseController {

    private final ISystemVariableService systemVariableService;

    @Autowired
    public DictionaryCategoryController(ISystemVariableService systemVariableService) {
        this.systemVariableService = systemVariableService;
    }

    /**
     * 分页查询字典类别
     * @param pageRequest
     * @param request
     * @return
     */
    @GetMapping("/dict-categories")
    @PreAuthorize("hasAuthority('dictionary-category:list')")
    public ApiResult getDictCategorys(PageRequest pageRequest, HttpServletRequest request){
        return success(systemVariableService.findDictionaryCategories(pageRequest, PropertyFilters.get(request, true)));
    }

    /**
     * 更新或保存字典类别
     * @param entity
     * @return
     */
    @PostMapping("/dict-category")
    @PreAuthorize("hasAuthority('dictionary-category:save')")
    public ApiResult saveDictCategory(@Valid @RequestBody DictionaryCategory entity){
        systemVariableService.saveDictionaryCategory(entity);
        return success(entity);
    }

    /**
     * 删除字典类别
     * @param id
     * @return
     */
    @DeleteMapping("/dict-category/{id}")
    @PreAuthorize("hasAuthority('dictionary-category:delete')")
    public ApiResult deleteCategoryById(@PathVariable("id") Long id){
        // TODO: 2019-01-02 校验是否下挂项目 
        systemVariableService.deleteDictionaryCategories(Lists.newArrayList(id));
        return success(id);
    }
}
