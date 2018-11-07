package com.funcas.pboot.module.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funcas.pboot.common.BaseEntity;
import com.funcas.pboot.module.sys.enumeration.JobState;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@EqualsAndHashCode(callSuper = false)
@TableName("tb_quartz_job")
@Data
public class QuartzJob extends BaseEntity<Long> {

    private String cronExpression;
    private String remark;
    private String jobClassName;
    private String parameter;
    private Integer state;
    private Date ctime;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long created;
    private Integer delFlag;


    public String getJobStateName(){
        return VariableUtils.getName(JobState.class, this.state);
    }
}
