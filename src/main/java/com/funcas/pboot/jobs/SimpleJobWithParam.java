package com.funcas.pboot.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月05日
 */
@Slf4j
public class SimpleJobWithParam implements Job {

    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("测试定时器{}", this.parameter);
    }
}
