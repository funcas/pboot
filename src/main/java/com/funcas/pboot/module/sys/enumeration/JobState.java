package com.funcas.pboot.module.sys.enumeration;

import com.funcas.pboot.common.enumeration.ValueEnum;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月05日
 */
public enum JobState implements ValueEnum<Integer>{
    /**
     * 运行中
     */
    RUNNING(1, "运行中"),
    /**
     * 暂停
     */
    PAUSE(2, "暂停"),
    /**
     * 停止
     */
    STOPED(3, "停止");


    Integer value;
    String name;
    JobState(Integer value, String name){
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
