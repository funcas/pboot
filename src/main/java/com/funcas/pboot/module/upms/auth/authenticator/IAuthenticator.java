package com.funcas.pboot.module.upms.auth.authenticator;

import com.funcas.pboot.module.upms.auth.IntegrationAuthentication;
import com.funcas.pboot.module.upms.entity.User;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
public interface IAuthenticator {
    /**
     * 处理集成认证
     * @param integrationAuthentication
     * @return
     */
    User authenticate(IntegrationAuthentication integrationAuthentication);


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /** 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);
}
