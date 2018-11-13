package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.common.exception.ServiceException;
import com.funcas.pboot.module.upms.entity.*;
import com.funcas.pboot.module.upms.service.IAccountService;
import com.funcas.pboot.module.upms.service.IUnitService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月18日
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IAccountService userService;
    private final IUnitService unitService;

    @Autowired
    public UserDetailsServiceImpl(IAccountService userService, IUnitService unitService) {
        this.userService = userService;
        this.unitService = unitService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User userVO = userService.getUserByUsername(name);
        if(userVO == null) {
            throw new ServiceException("用户不存在");
        }

        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
        List<Resource> resourceList = userService.getUserResources(userVO.getId());
        for(Resource resource : resourceList) {
            if(StringUtils.isNotEmpty(resource.getPermission())){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(resource.getPermission());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        List<Group> groups = userService.getUserGroups(userVO.getId());
        Unit unit = unitService.selectOne(userVO.getUnitId());
        userVO.setGroups(groups);
        userVO.setOrganization(unit);
        // 可用性 :true:可用 false:不可用
        boolean enabled = State.ENABLE.getValue().equals(userVO.getState());

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(userVO.getUsername(), userVO.getPassword() ,enabled,true,
                true,true, grantedAuthorities);
        return new BaseUserDetail(userVO, user);
    }
}
