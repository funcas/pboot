package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Resource;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @GetMapping("/resources")
    public ApiResult getUserResources() {
        return success(accountService.mergeResources(accountService.getResources()));
    }

    @GetMapping("/resource/{id}")
    public ApiResult edit(@PathVariable("id") Long id) {
        return success(accountService.getResource(id));
    }

    @PostMapping("/resource")
    public ApiResult save(@RequestBody Resource entity) {
        accountService.saveResource(entity);
        return success(entity);
    }

    @DeleteMapping("/resource/{id}")
    public ApiResult delete(@PathVariable("id") Long id) {
        accountService.deleteResources(Lists.newArrayList(id));
        return success(id);
    }

    @GetMapping("/resource/group/{id}")
    public ApiResult getResourceByGroupId(@PathVariable("id") Long id){
        return success(accountService.getResourcesByGroupId(id));
    }
}
