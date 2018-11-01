package com.funcas.pboot.module.upms.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月31日
 */
@Data
public class ResourceDTO {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String title;
    private Boolean expand;
    private Boolean selected;

}
