package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月09日
 */
@JSONType(ignores = {"parent"})
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_resource")
public class Resource extends BaseEntity<Long> {

    private String component;
    private String permission;
    private String remark;
    private Integer sort;
    private String name;
    private Integer type;
    private String value;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fkParentId;
    private String icon;
    @TableField(exist = false)
    private List<Resource> children = Lists.newArrayList();

    private transient Resource parent;
    @TableField(exist = false)
    private Boolean checked;

    public String getTitle(){
        return this.name;
    }
    public String getLabel(){
        return this.name;
    }

}
