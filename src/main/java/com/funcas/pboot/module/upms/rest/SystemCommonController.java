package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.upms.service.ISystemCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月09日
 */
@RestController
@Slf4j
public class SystemCommonController extends BaseController {

    private final ISystemCommonService systemCommonService;

    @Autowired
    public SystemCommonController(ISystemCommonService systemCommonService) {
        this.systemCommonService = systemCommonService;
    }

    /**
     * 登出操作
     * @param authentication
     * @return
     */
    @PostMapping("/_logout")
    public ApiResult logout(OAuth2Authentication authentication) {
        boolean result = systemCommonService.revokeAccessToken(authentication);
        return success(result);
    }
}
