package com.funcas.pboot.module.upms.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月09日
 */
@TableName("tb_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = -2269302169894751895L;

    private String email;
    @JsonIgnore
    private String password;
    private String nickname;
    private Integer state;
    private String username;
    private String avatar;
    private String phone;
    private int sex;
    private String birthday;
    private String address;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orgId;
    @TableField(exist = false)
    private List<Group> groups;
    private transient Unit organization;

    @TableField(exist = false)
    private List<String> perms;

}
