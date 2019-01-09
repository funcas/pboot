package com.funcas.pboot.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funcas.pboot.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年04月19日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_dictionary_category")
public class DictionaryCategory extends BaseEntity<Long> {

    private static final long serialVersionUID = -7569795294713771393L;
    @NotNull
    @Length(max = 16)
    private String code;
    @Length(max = 32)
    private String name;
    private String remark;
    private boolean removeAble;
    private boolean editAble;

}
