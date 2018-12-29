package com.funcas.pboot.module.upms.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.common.exception.ServiceException;
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
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 用户管理rest
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

    /**
     * 获取登陆用户信息接口
     * @param authentication
     * @return
     */
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

    /**
     * 获取用户列表接口
     * @param pageRequest
     * @param filter
     * @return
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:list')")
    public ApiResult getUserLists(PageRequest pageRequest, @RequestParam Map<String,Object> filter){
        IPage<User> userIPage = accountService.findUsers(pageRequest, filter);
        return success(userIPage);
    }

    /**
     * 按昵称获取用户列表
     * @param nickname
     * @return
     */
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('user:list')")
    public ApiResult getAllUserLists(String nickname){
        List<User> users = accountService.findUsersByNickname(nickname);
        return success(users);
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('user:edit')")
    public ApiResult modifyUser(@PathVariable("userId") Long userId){
        User user = accountService.getUser(userId);
        return success(user);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('user:save')")
    public ApiResult saveUser(@Valid @RequestBody User user) {
        accountService.saveUser(user);
        return success(user);
    }

    /**
     * 根据用户id删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ApiResult deleteUser(@PathVariable("userId") Long userId){
        accountService.deleteUsers(Lists.newArrayList(userId));
        return success(userId);
    }

    /**
     * 批量删除用户（物理）
     * @param ids
     * @return
     */
    @DeleteMapping("/user/batch")
    public ApiResult deleteUserBatch(@RequestBody List<Long> ids){
        accountService.deleteUsers(ids);
        return success(ids);
    }


}
