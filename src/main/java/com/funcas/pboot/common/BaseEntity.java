package com.funcas.pboot.common;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @author funcas(fss_34@163.com)
 * @version 1.0
 * @date 2016年11月25日
 */
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
