package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.module.ServiceTestCaseSupport;
import com.funcas.pboot.module.sys.entity.QuartzJob;
import com.funcas.pboot.module.sys.service.IJobService;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class JobServiceTest extends ServiceTestCaseSupport {

    public static final String TABLE_NAME = "tb_quartz_job";

    @Autowired
    private IJobService jobService;

    private QuartzJob generateJob() {
        QuartzJob job = new QuartzJob();
        job.setJobClassName("com.funcas.pboot.jobs");
        job.setState(State.ENABLE.getValue());
        job.setCronExpression("0/15 * * * * ?");
        job.setDelFlag(0);
        return job;
    }

    @Test
    public void find() {
        int total = countRowsInTable(TABLE_NAME);
        IPage<QuartzJob> jobs = jobService.find(new PageRequest(1, 999), Maps.newHashMap());
        Assert.assertEquals(jobs.getTotal(), total);
    }

    @Test
    @Transactional
    public void saveJob() {
        jobService.saveJob(this.generateJob());
    }

    @Test
    public void startJob() {
    }

    @Test
    public void pauseJob() {
    }

    @Test
    public void resumeJob() {
    }

    @Test
    public void stopJob() {
    }

    @Test
    public void removeJob() {
    }
}