package com.funcas.pboot.module.upms.auth.authenticator.support;

import com.funcas.pboot.module.upms.auth.IntegrationAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
@Slf4j
@Component
public class VerificationCodeAuthenticator extends UsernamePasswordAuthenticator{
    private final static String VERIFICATION_CODE_AUTH_TYPE = "vc";


    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String vcToken = integrationAuthentication.getAuthParameter("vc_token");
        String vcCode = integrationAuthentication.getAuthParameter("vc_code");
        log.info(vcCode);
        log.info(vcToken);
        //验证验证码
//        Result<Boolean> result = verificationCodeClient.validate(vcToken, vcCode, null);
//        if (!result.getData()) {
//            throw new OAuth2Exception("验证码错误");
//        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
