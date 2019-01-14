package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月14日
 */
@RestController
@RequestMapping("/sys")
public class UnitController extends BaseController {

    private final IUnitService unitService;

    @Autowired
    public UnitController(IUnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * 根据父id获取单位节点
     * @param pid
     * @return
     */
    @GetMapping("/unit/tree/{pid}")
    public ApiResult getOrgByParentId(@PathVariable("pid") Long pid) {
        return success(unitService.getUnitByParentId(pid));
    }

    /**
     * 获取单位树信息
     * @return
     */
    @GetMapping("/units")
    @PreAuthorize("hasAuthority('unit:list')")
    public ApiResult getAllUnits() {
        return success(unitService.getAllUnits());
    }

    /**
     * 保存组织机构
     * @param entity
     * @return
     */
    @PostMapping("/unit")
    @PreAuthorize("hasAuthority('unit:save')")
    public ApiResult saveUnit(@Valid @RequestBody Unit entity){
        unitService.saveUnit(entity);
        return success(entity);
    }

    /**
     * 根据id删除组织机构
     * @param id
     * @return
     */
    @DeleteMapping("/unit/{id}")
    @PreAuthorize("hasAuthority('unit:delete')")
    public ApiResult deleteUnit(@PathVariable("id") Long id){
        unitService.deleteUnit(id);
        return success(id);
    }

    /**
     * 根据组id获取关联单位信息
     * @param id
     * @return
     */
    @GetMapping("/unit/group/{id}")
    public ApiResult getGroupUnits(@PathVariable("id") Long id) {
        List<Unit> unitList = unitService.getGroupUnit(id);
        return success(unitList);
    }

    @GetMapping("/unit/checked")
    public ApiResult getCheckedUnit(@RequestParam("id") Long groupId) {
        return success(unitService.getCheckedUnitsByGroupId(groupId));
    }
}
