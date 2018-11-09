package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.module.upms.service.ISystemCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月09日
 */
@Service
public class SystemCommonSerivceImpl implements ISystemCommonService {

    private final RedisTokenStore tokenStore;

    @Autowired
    public SystemCommonSerivceImpl(RedisTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public boolean revokeAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = tokenStore.getAccessToken(authentication);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }
}
