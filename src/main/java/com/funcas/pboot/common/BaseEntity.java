package com.funcas.pboot.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author funcas(fss_34@163.com)
 * @version 1.0
 * @date 2016年11月25日
 */
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
