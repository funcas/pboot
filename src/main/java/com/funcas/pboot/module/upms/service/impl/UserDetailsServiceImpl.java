package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.module.upms.auth.AuthenticationContext;
import com.funcas.pboot.module.upms.auth.IntegrationAuthentication;
import com.funcas.pboot.module.upms.auth.authenticator.IAuthenticator;
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

    public static final String DEFAULT_PERMS = "sys:default";

    private final IAccountService userService;
    private final IUnitService unitService;
    private final List<IAuthenticator> authenticators;

    @Autowired
    public UserDetailsServiceImpl(IAccountService userService, IUnitService unitService, List<IAuthenticator> authenticators) {
        this.userService = userService;
        this.unitService = unitService;
        this.authenticators = authenticators;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        IntegrationAuthentication integrationAuthentication = AuthenticationContext.get();
        //判断是否是集成登录
        if (integrationAuthentication == null) {
            integrationAuthentication = new IntegrationAuthentication();
        }

        integrationAuthentication.setUsername(name);
        User userVO = this.authenticate(integrationAuthentication);

        if(userVO == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        this.grantAuthorities(userVO);
        // 可用性 :true:可用 false:不可用
        boolean enabled = State.ENABLE.getValue().equals(userVO.getState());

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(userVO.getUsername(), userVO.getPassword() ,enabled,true,
                true,true, userVO.getGrantedAuthorities());
        return new BaseUserDetail(userVO, user);
    }


    protected void grantAuthorities(User userVO){
        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
        List<Resource> resourceList = userService.getUserResources(userVO.getId());
        for(Resource resource : resourceList) {
            if(StringUtils.isNotEmpty(resource.getPermission())){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(resource.getPermission());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        grantedAuthorities.add(new SimpleGrantedAuthority(DEFAULT_PERMS));
        List<Group> groups = userService.getUserGroups(userVO.getId());
        Unit unit = unitService.selectOne(userVO.getUnitId());
        userVO.setGroups(groups);
        userVO.setOrganization(unit);
        userVO.setGrantedAuthorities(grantedAuthorities);
    }

    private User authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }
}
