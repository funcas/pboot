package com.funcas.pboot.module.upms.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月09日
 */
@TableName(User.ALIAS)
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity<Long> {

    public static final String ALIAS = "tb_user";
    private static final long serialVersionUID = -2269302169894751895L;
    @Length(max = 64)
    @Email
    private String email;

    private String password;

    @Length(max = 16)
    private String nickname;
    @NotNull
    private Integer state;
    @NotNull
    @Length(max = 16)
    private String username;
    @Length(max = 255)
    private String avatar;
    @Length(max = 16)
    private String phone;
    private int sex;
    @Length(max = 10)
    private String birthday;
    @Length(max = 128)
    private String address;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long unitId;
    @TableField(exist = false)
    private List<Group> groups;
    @TableField(exist = false)
    private Unit unit;
    private String openid;
    @TableField(exist = false)
    private List<String> perms;

    private transient Set<GrantedAuthority> grantedAuthorities;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStateName() {
        return VariableUtils.getName(State.class, this.getState());
    }
}
