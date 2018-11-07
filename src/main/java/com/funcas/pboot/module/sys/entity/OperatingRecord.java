package com.funcas.pboot.module.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funcas.pboot.module.sys.enumeration.OperatingState;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作记录实体，记录用户的操作信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_operating_record")
public class OperatingRecord {
	
	private static final long serialVersionUID = 1L;
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
	private String username;
	private String fkUserId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss SSS")
	private Date startDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss SSS")
	private Date endDate;
	private String operatingTarget;
	private String ip;
	private String method;
	private Integer state;
	private String module;
	private String func;
	private String remark;

	public String getStateName(){
		return VariableUtils.getName(OperatingState.class, this.state);
	}
}
