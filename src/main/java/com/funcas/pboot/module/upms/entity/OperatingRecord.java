package com.funcas.pboot.module.upms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 操作记录实体，记录用户的操作信息
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperatingRecord {
	
	private static final long serialVersionUID = 1L;
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
	//操作人名称
	private String username;
	//操作人主键id
	private String fkUserId;
	//开始操作时间
	private Date startDate;
	//操作结束时间
	private Date endDate;
	//操作目标
	private String operatingTarget;
	//ip地址
	private String ip;
	//操作的java方法
	private String method;
	//执行状态,1代表成，2代表执行时出现异常
	private Integer state;
	//模块名称
	private String module;
	//功能名称
	private String function;
	//描述
	private String remark;
}
