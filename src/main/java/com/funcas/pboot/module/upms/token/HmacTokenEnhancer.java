package com.funcas.pboot.module.upms.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funcas.pboot.common.util.EncodeUtils;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.common.util.IdWorker;
import com.funcas.pboot.common.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月08日
 */
@Slf4j
public class HmacTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = ((DefaultOAuth2AccessToken) accessToken);
            token.setValue(generateToken(authentication.getOAuth2Request().getClientId()));

            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken instanceof DefaultOAuth2RefreshToken) {
                token.setRefreshToken(new DefaultOAuth2RefreshToken(generateToken(authentication.getOAuth2Request().getClientId())));
            }
            return token;
        }
        return accessToken;
    }


    private String generateToken(String key) {
        return EncodeUtils.encodeBase64(HmacUtils.hmacSha256(key, IdWorker.getIdAsString()));
    }
}


