package com.funcas.pboot.common.enumeration.entity;


import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * 状态枚举
 *
 */
public enum State implements ValueEnum<Integer> {

    /**
     * 启用
     */
    ENABLE(1, "启用"),
    /**
     * 禁用
     */
    DISABLE(2, "禁用");

    //值
    private Integer value;
    //名称
    private String name;

    State(Integer value, String name) {
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
