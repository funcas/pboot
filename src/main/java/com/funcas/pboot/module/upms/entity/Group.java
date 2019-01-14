package com.funcas.pboot.module.upms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月09日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_group")
public class Group extends BaseEntity<Long> {

    private static final long serialVersionUID = -1589249335874361382L;

    @NotNull
    private String name;
    private String remark;
    private Integer dataScope;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;
    @TableField(exist = false)
    private List<Long> resourceIds;
    @TableField(exist = false)
    private List<Long> unitIds;

}
