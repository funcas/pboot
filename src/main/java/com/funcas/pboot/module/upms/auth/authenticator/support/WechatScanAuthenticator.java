package com.funcas.pboot.module.upms.auth.authenticator.support;

import com.funcas.pboot.common.exception.ServiceException;
import com.funcas.pboot.module.upms.auth.IntegrationAuthentication;
import com.funcas.pboot.module.upms.auth.authenticator.AbstractPreparableAuthenticator;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
@Slf4j
@Component
public class WechatScanAuthenticator extends AbstractPreparableAuthenticator {

    private final static String VERIFICATION_CODE_AUTH_TYPE = "wx_scan";

    @Autowired
    private IAccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(IntegrationAuthentication integrationAuthentication) {
        String ticket = integrationAuthentication.getAuthParameter("password");
        String openid = integrationAuthentication.getAuthParameter("username");

        User user = accountService.findUserByOpenid(openid);
        if(user != null){
            user.setPassword(passwordEncoder.encode(ticket));
        }else{
            throw new ServiceException("认证异常");
        }

        return user;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
