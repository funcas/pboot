package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Resource;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月14日
 */
@RestController
@RequestMapping("/sys")
public class ResourceController extends BaseController {
    private final IAccountService accountService;

    @Autowired
    public ResourceController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 获取所有的菜单信息
     * @return
     */
    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('resource:list')")
    public ApiResult getUserResources(Authentication authentication) {
//        Long uid = ((BaseUserDetail)authentication.getPrincipal()).getBaseUser().getId();
        return success(accountService.mergeResources(accountService.getResources()));
    }

    /**
     * 根据id获取菜单信息
     * @param id
     * @return
     */
    @GetMapping("/resource/{id}")
    @PreAuthorize("hasAuthority('resource:edit')")
    public ApiResult edit(@PathVariable("id") Long id) {
        return success(accountService.getResource(id));
    }

    /**
     * 保存菜单信息
     * @param entity
     * @return
     */
    @PostMapping("/resource")
    @PreAuthorize("hasAuthority('resource:save')")
    public ApiResult save(@Valid @RequestBody Resource entity) {
        accountService.saveResource(entity);
        return success(entity);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/resource/{id}")
    @PreAuthorize("hasAuthority('resource:delete')")
    public ApiResult delete(@PathVariable("id") Long id) {
        accountService.deleteResources(Lists.newArrayList(id));
        return success(id);
    }

    /**
     * 根据组id获取资源，带是否选中
     * @param id
     * @return
     */
    @GetMapping("/resource/group/{id}")
    public ApiResult getResourceByGroupId(@PathVariable("id") Long id){
        return success(accountService.getResourcesByGroupId(id));
    }

    @GetMapping("/resource/checked")
    public ApiResult getResourceIdsByGroupId(@RequestParam("id") Long id) {
        return success(accountService.getCheckedResourceIds(id));
    }


}
