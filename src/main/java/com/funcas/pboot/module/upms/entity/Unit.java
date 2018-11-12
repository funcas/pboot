package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月21日
 */
@TableName("tb_unit")
@Data
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseEntity<Long> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    private String orgCode;
    private String name;
    private Integer sort;
    private String master;
    private String phone;
    private Integer state;
    private String remarks;
    private Integer delFlag;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;
    private Date ctime;
    private Date mtime;
    private String code;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifierId;
    @JsonIgnore
    private Integer leaf;
    @TableField(exist = false)
    private List<Unit> children = Lists.newArrayList();

    @TableField(exist = false)
    private Boolean checked;

    public String getTitle(){
        return this.name;
    }

    public String getLabel() {
        return this.name;
    }


    public void addChildren(Unit unit){
        this.children.add(unit);
    }
}
