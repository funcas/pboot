package com.funcas.pboot.module.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月19日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_dictionary_category")
public class DictionaryCategory extends BaseEntity<Long> {

    private String code;
    private String name;
    private String remark;

}
