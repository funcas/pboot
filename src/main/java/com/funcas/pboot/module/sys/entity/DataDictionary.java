package com.funcas.pboot.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月15日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_data_dictionary")
public class DataDictionary extends BaseEntity<Long> {

    private static final long serialVersionUID = -3831565201229237677L;
    @NotNull
    @Length(max = 16)
    private String name;
    @Length(max = 32)
    private String code;
    private String remark;
    @Length(max = 32)
    private String type;
    @Length(max = 64)
    private String value;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fkCategoryId;

    @TableField(exist = false)
    private String categoryName;

}
