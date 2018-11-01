package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long parentId;
    private String orgCode;
    private String name;
    private Integer sort;
    private String master;
    private String phone;
    private Integer state;
    private String remarks;
    private Integer delFlag;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long creatorId;
    private Date ctime;
    private Date mtime;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long modifierId;
    @JSONField(serialize = false)
    private Integer leaf;
    @TableField(exist = false)
    private List<Unit> children = Lists.newArrayList();

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
