package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private static final long serialVersionUID = 9069295891223054114L;
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

    @JsonIgnore
    private transient Resource parent;
    @TableField(exist = false)
    private Boolean checked;
    @TableField(exist = false)
    private Integer leafCount;

    public String getTitle(){
        return this.name;
    }
    public String getLabel(){
        return this.name;
    }

    public Boolean getChecked() {
        return (leafCount != null && leafCount == 0 && this.checked);
    }

    public Boolean isLeaf() {
        return leafCount != null && leafCount == 0;
    }

}
