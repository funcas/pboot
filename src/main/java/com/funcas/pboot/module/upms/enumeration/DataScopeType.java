package com.funcas.pboot.module.upms.enumeration;


import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年05月03日
 */
public enum DataScopeType implements ValueEnum<Integer> {
    /**
     * 全部数据权限
     */
    SCOPE_ALL(0, "全部"),
    /**
     * 部门及下属部门
     */
    SCOPE_ORG_CHILDREN(1, "部门及下属部门"),
    /**
     * 本部
     */
    SCOPE_ORG(2, "本部"),
    /**
     * 个人
     */
    SCOPE_SELF(3, "仅个人"),
    /**
     * 自定义
     */
    SCOPE_CUSTOM(9, "自定义"),
    /**
     * 无权限
     */
    SCOPE_NONE(99, "无权限");

    private Integer value;
    private String name;

    DataScopeType(Integer value, String name){
        this.value = value;
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
