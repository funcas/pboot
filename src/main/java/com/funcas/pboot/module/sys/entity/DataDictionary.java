package com.funcas.pboot.module.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @NotNull
    private String name;
    private String code;
    private String remark;
    private String type;
    private String value;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long fkCategoryId;

    @TableField(exist = false)
    private String categoryName;

}
