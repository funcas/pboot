package com.funcas.pboot.common.enumeration;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 字段类型枚举
 *
 * @author maurice
 */
public enum FieldType implements ValueEnum<Class<?>> {

    /**
     * String 类型
     */
    S("STRING", String.class),
    /**
     * Integer 类型
     */
    I("INTEGER", Integer.class),
    /**
     * Long 类型
     */
    L("LONG", Long.class),
    /**
     * Double 类型
     */
    F("DOUBLE", Double.class),
    /**
     * Date 类型
     */
    D("DATE", Date.class),
    /**
     * Boolean 类型
     */
    B("BOOLEAN", Boolean.class),
    /**
     * BigDecimal 类型
     */
    M("DECIMAL",BigDecimal .class);

    // 名称
    private String name;

    // 类型
    private Class<?> value;

    /**
     * 字段类型枚举
     *
     * @param name  名称
     * @param value 类型
     */
    FieldType(String name, Class<?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Class<?> getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取 Class 对象
     *
     * @param name 类名称
     * @return 对应的 Clsas 对象
     */
    public static Class<?> getClass(String name) {

        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getValue().getName().equals(name)) {
                return fieldType.getValue();
            }
        }

        return null;
    }
}
