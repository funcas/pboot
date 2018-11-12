package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月14日
 */
@RestController
@RequestMapping("/sys")
public class UnitController extends BaseController {

    @Autowired
    private IUnitService unitService;

    @GetMapping("/unit/tree/{pid}")
    public ApiResult getOrgByParentId(@PathVariable("pid") Long pid) {
        return success(unitService.getUnitByParentId(pid));
    }

    @GetMapping("/units")
    @PreAuthorize("hasAuthority('unit:list')")
    public ApiResult getAllUnits() {
        return success(unitService.getAllUnits());
    }

    @GetMapping("/org/org-leave")
    public ApiResult getOrgLeave() {
        return success(unitService.findLeaveUnit());
    }

    @PostMapping("/unit")
    @PreAuthorize("hasAuthority('unit:save')")
    public ApiResult saveUnit(@RequestBody Unit entity){
        unitService.saveUnit(entity);
        return success(entity);
    }

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
}
