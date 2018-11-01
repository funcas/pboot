package com.funcas.pboot.common.enumeration;

/**
 * 针对键为String，值为任意Object类型的枚举接口父类
 *
 * @param <V>
 */
public interface ValueEnum<V> {

    /**
     * 获取值
     *
     * @return 值
     */
    public V getValue();

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName();
}

