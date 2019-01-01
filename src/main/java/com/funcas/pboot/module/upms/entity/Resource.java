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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
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
    @Length(max = 64)
    private String component;
    @Length(max = 64)
    private String permission;
    private String remark;
    private Integer sort;
    @Length(max = 16)
    private String name;
    @Max(2)
    private Integer type;
    @Length(max = 256)
    private String value;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fkParentId;
    @Length(max = 32)
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
