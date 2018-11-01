package com.funcas.pboot.common.enumeration;

/**
 * date: 16/4/19 上午9:46
 *
 * @author funcas
 * @version 1.0
 */
public enum ApiResultEnum implements ValueEnum<String> {
    SUCCESS("000","操作成功"),
    VALIDATION_FAILURE("001","校验不通过"),
    ACL_FAILURE("002","用户名或密码不正确"),
    NO_RESPONSE("003","无应答"),
    UNAUTHORIZED("007","未授权"),
    NO_RECORD("010","没有找到相关记录"),
    SERVICE_EXCEPTION("500", "服务运行异常"),
    UNKNOWN_EXCEPTION("900", "未知服务运行异常"),
    FAILURE("999","未知服务异常");

    private String name;
    private String value;

    private ApiResultEnum(String value, String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
