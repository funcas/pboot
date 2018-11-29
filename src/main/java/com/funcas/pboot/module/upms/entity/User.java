package com.funcas.pboot.module.upms.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

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
    private Long unitId;
    @TableField(exist = false)
    private List<Group> groups;
    @TableField(exist = false)
    private Unit organization;
    private String openid;
    @TableField(exist = false)
    private List<String> perms;

    private transient Set<GrantedAuthority> grantedAuthorities;

}
