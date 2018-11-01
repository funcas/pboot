package com.funcas.pboot.module.upms.entity;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月20日
 */
public class BaseUserDetail implements UserDetails, CredentialsContainer {

    private final com.funcas.pboot.module.upms.entity.User userVO;
    private final org.springframework.security.core.userdetails.User user;

    public BaseUserDetail(com.funcas.pboot.module.upms.entity.User userVO, User user) {
        this.userVO = userVO;
        this.user = user;
    }


    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public com.funcas.pboot.module.upms.entity.User getBaseUser() {
        return userVO;
    }
}
