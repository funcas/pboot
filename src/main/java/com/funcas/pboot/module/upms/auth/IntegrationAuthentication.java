package com.funcas.pboot.module.upms.auth;

import lombok.Data;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Data
public class IntegrationAuthentication {

    private String authType;
    private String username;
    private Map<String,String[]> authParameters;

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }
}
