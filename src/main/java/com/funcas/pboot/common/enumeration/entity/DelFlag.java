package com.funcas.pboot.common.enumeration.entity;

import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月01日
 */
public enum DelFlag implements ValueEnum<Integer> {
    /**
     * 启用
     */
    NORMAL(0, "正常"),
    /**
     * 禁用
     */
    DELETED(1, "删除");

    //值
    private Integer value;
    //名称
    private String name;

    DelFlag(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取值
     *
     * @return Integer
     */
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * 获取名称
     *
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }
}
