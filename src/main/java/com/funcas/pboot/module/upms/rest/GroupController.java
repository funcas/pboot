package com.funcas.pboot.module.upms.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月14日
 */
@RestController
@RequestMapping("/sys")
public class GroupController extends BaseController {

    private final IAccountService accountService;

    @Autowired
    public GroupController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 分页获取组列表
     * @param pageRequest
     * @param filter
     * @return
     */
    @GetMapping("/groups")
    @PreAuthorize("hasAuthority('group:list')")
    public ApiResult list(PageRequest pageRequest, @RequestParam Map<String, Object> filter) {
        IPage<Group> groupList = accountService.findGroups(pageRequest, filter);
        return success(groupList);
    }

    /**
     * 根据id获取组信息
     * @param id
     * @return
     */
    @GetMapping("/group/{id}")
    @PreAuthorize("hasAuthority('group:edit')")
    public ApiResult edit(@PathVariable("id") Long id) {
        return success(accountService.getGroup(id));
    }

    /**
     * 保存组信息
     * @param group
     * @return
     */
    @PostMapping("/group")
    @PreAuthorize("hasAuthority('group:save')")
    public ApiResult save(@RequestBody Group group) {
        accountService.saveGroup(group);
        return success(group);
    }

    /**
     * 按id删除组信息
     * @param id
     * @return
     */
    @DeleteMapping("/group/{id}")
    @PreAuthorize("hasAuthority('group:delete')")
    public ApiResult delete(@PathVariable("id") Long id) {
        accountService.deleteGroups(Lists.newArrayList(id));
        return success(id);
    }

    /**
     * 批量删除组
     * @param ids
     * @return
     */
    @DeleteMapping("/group/batch")
    @PreAuthorize("hasAuthority('group:delete')")
    public ApiResult deleteBatch(List<Long> ids) {
        accountService.deleteGroups(ids);
        return success(ids);
    }
}
