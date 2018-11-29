package com.funcas.pboot.module.upms.enumeration;

import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月28日
 */
public enum QrLoginCode implements ValueEnum<Integer> {
    /**
     * 登陆成功
     */
    SUCCESS("登陆成功", 200),
    /**
     * 扫描成功
     */
    SCANED("扫描成功", 201),
    /**
     * 超时
     */
    TIMEOUT("超时", 408);

    Integer value;
    String name;

    private QrLoginCode(String name, Integer code){
        this.value = code;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
