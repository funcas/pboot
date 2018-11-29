package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月27日
 */
@Data
public class QrLoginMessage {



    @JsonIgnore
    private String id;
    private Integer code;
    @JsonProperty("_t")
    private Long timestamp;
    private Object ext;

}
