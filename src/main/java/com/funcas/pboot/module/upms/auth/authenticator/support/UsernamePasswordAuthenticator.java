package com.funcas.pboot.module.upms.auth.authenticator.support;

import com.funcas.pboot.module.upms.auth.IntegrationAuthentication;
import com.funcas.pboot.module.upms.auth.authenticator.AbstractPreparableAuthenticator;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.service.IAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
@Primary
@Component
public class UsernamePasswordAuthenticator extends AbstractPreparableAuthenticator {

    @Autowired
    private IAccountService accountService;

    @Override
    public User authenticate(IntegrationAuthentication integrationAuthentication) {
        return accountService.getUserByUsername(integrationAuthentication.getUsername());
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }
}
