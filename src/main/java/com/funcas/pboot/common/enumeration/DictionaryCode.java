package com.funcas.pboot.common.enumeration;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月20日
 */
public enum DictionaryCode {
    /**
     * 状态类型
     */
    State("state"),

    /**
     * 资源类型
     */
    ResourceType("resource-type"),

    /**
     * 组类型
     */
    GroupType("group-type"),

    /**
     * 属性值类型
     */
    ValueType("value-type"),

    /**
     * 操作状态类型
     */
    OperatingState("operating-state");

    private String code;

    private DictionaryCode(String code) {
        this.code = code;
    }

    /**
     * 获取类型代码
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }
}
