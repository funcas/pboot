package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ApiResult getAllUnits() {
        return success(unitService.getAllUnits());
    }

    @GetMapping("/org/org-leave")
    public ApiResult getOrgLeave() {
        return success(unitService.findLeaveUnit());
    }

    @PostMapping("/unit")
    public ApiResult saveUnit(@RequestBody Unit entity){
        unitService.saveUnit(entity);
        return success(entity);
    }

    @DeleteMapping("/unit/{id}")
    public ApiResult deleteUnit(@PathVariable("id") Long id){
        unitService.deleteUnit(id);
        return success(id);
    }
}
