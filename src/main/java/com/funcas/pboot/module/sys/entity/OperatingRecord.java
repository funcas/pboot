package com.funcas.pboot.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.funcas.pboot.common.BaseEntity;
import com.funcas.pboot.module.sys.enumeration.OperatingState;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作记录实体，记录用户的操作信息
 *
 * @author funcas
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_operating_record")
public class OperatingRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = -5647204682516294234L;
	private String username;
	private String fkUserId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS")
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
