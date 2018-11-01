package com.funcas.pboot.common.enumeration.entity;


import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * 资源类型枚举
 *
 */
public enum ResourceType implements ValueEnum<Integer> {

    /**
     * 导航类型，该类型为用户可以见的
     */
    MENU(1, "导航类型"),
    /**
     * 安全类型，该类型为 shiro 拦截的并且用户不可见的
     */
    SECURITY(2, "安全类型");

    ResourceType(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private Integer value;

    /**
     * 获取资源类型名称
     *
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取资源类型值
     *
     * @return Integer
     */
    @Override
    public Integer getValue() {
        return value;
    }


}
