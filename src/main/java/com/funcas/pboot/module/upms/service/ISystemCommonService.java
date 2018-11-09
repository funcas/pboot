package com.funcas.pboot.module.upms.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月09日
 */
public interface ISystemCommonService {

    /**
     * 回收accessToken
     * @param authentication
     * @return
     */
    public boolean revokeAccessToken(OAuth2Authentication authentication);
}
