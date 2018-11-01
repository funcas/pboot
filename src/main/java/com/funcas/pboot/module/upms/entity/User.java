package com.funcas.pboot.module.upms.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @JSONField(serialize = false)
    private String password;
    private String nickname;
    private Integer state;
    private String username;
    private String avatar;
    private String phone;
    private int sex;
    private String birthday;
    private String address;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orgId;
    @TableField(exist = false)
    private List<Group> groups;
    private transient Unit organization;

}
