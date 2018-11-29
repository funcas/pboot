package com.funcas.pboot.module.upms.auth;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
public class AuthenticationContext {

    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication){
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }
}
