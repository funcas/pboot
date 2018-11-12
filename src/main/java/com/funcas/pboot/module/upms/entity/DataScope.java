package com.funcas.pboot.module.upms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 数据范围
 *
 * @author funcas
 * @date 2017-07-23 22:19
 */
@Data
public class DataScope implements Serializable{

    private User user;
    private String bizTableName;
    private String bizOrgColumn = "org_id";
    private String bizUserColumn = "creator_id";
    private String ext;

    public Integer getMaxDataScopeValue(){
        if(this.user == null || this.user.getGroups() == null || this.user.getGroups().size() == 0) {
            return -1;
        }

        this.user.getGroups().sort(Comparator.comparingInt(Group::getDataScope));
        return this.user.getGroups().get(0).getDataScope();
    }
}
