package com.funcas.pboot.common;

import java.util.Date;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年05月14日
 */
public class BizEntity<T> extends BaseEntity<T>{
    protected Date createTime;
    protected Long creator;
    protected Date updateTime;
    protected Long updator;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }
}
