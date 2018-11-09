package com.funcas.pboot.module.upms.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.upms.entity.BaseUserDetail;
import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.entity.Resource;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.funcas.pboot.module.util.VariableUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月12日
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class UserController extends BaseController {

    private final IAccountService accountService;

    @Autowired
    public UserController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/userinfo")
    public ApiResult getUserInfo(Authentication authentication){
        Long uid = ((BaseUserDetail)authentication.getPrincipal()).getBaseUser().getId();
        User user = accountService.getUser(uid);
        List<Resource> resourceList = accountService.getUserResources(uid);
        List<String> perms = Lists.newArrayList();
        for(Resource resource : resourceList){
            if(StringUtils.isNotEmpty(resource.getPermission())){
                perms.add(resource.getPermission());
            }
        }
        user.setPerms(perms);
        return success(user);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:list')")
    public ApiResult getUserLists(PageRequest pageRequest, @RequestParam Map<String,Object> filter){
        IPage<User> userIPage = accountService.findUsers(pageRequest, filter);
        return success(userIPage);
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('user:list')")
    public ApiResult getAllUserLists(String nickname){
        List<User> users = accountService.findUsersByNickname(nickname);
        return success(users);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('user:edit')")
    public ApiResult modifyUser(@PathVariable("userId") Long userId){
        User user = accountService.getUser(userId);
//        List<Group> groupList = accountService.getUserGroups(userId);
//        user.setGroup(groupList);

        return success(user);
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('user:save')")
    public ApiResult saveUser(@RequestBody User user) {
        accountService.saveUser(user);
        return success(user);
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('test')")
    public ApiResult deleteUser(@PathVariable("userId") Long userId){
        accountService.deleteUsers(Lists.newArrayList(userId));
        return success(userId);
    }

    @DeleteMapping("/user/batch")
    public ApiResult deleteUserBatch(@RequestBody List<Long> ids){
        accountService.deleteUsers(ids);
        return success(ids);
    }

}
