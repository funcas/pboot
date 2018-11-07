package com.funcas.pboot.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.exception.ServiceException;
import com.funcas.pboot.common.util.IdWorker;
import com.funcas.pboot.jobs.JobConstants;
import com.funcas.pboot.module.sys.entity.QuartzJob;
import com.funcas.pboot.module.sys.enumeration.JobState;
import com.funcas.pboot.module.sys.mapper.QuartzJobMapper;
import com.funcas.pboot.module.sys.service.IJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@Slf4j
@Service
public class JobService implements IJobService {

    private final Scheduler scheduler;
    private final QuartzJobMapper quartzJobMapper;

    @Autowired
    public JobService(Scheduler scheduler, QuartzJobMapper quartzJobMapper) {
        this.scheduler = scheduler;
        this.quartzJobMapper = quartzJobMapper;
    }

    /**
     * 分页查询定时任务
     * @param pageRequest
     * @param filter
     * @return
     */
    @Override
    public IPage<QuartzJob> find(PageRequest pageRequest, Map<String, Object> filter) {
        QueryWrapper<QuartzJob> wrapper = new QueryWrapper<>();
        return quartzJobMapper.selectPage(new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize()), wrapper);
    }

    /**
     * 更新或删除定时任务
     * @param job
     */
    @Override
    public void saveJob(QuartzJob job) {
        if(job.getId() != null){
            quartzJobMapper.updateById(job);
        }else{
            job.setId(IdWorker.getId());
            job.setCtime(new Date());
            job.setState(JobState.STOPED.getValue());
            quartzJobMapper.insert(job);
        }
    }

    /**
     * 启动定时器
     *
     * @param jobId
     */
    @Override
    public void startJob(Long jobId) {
        QuartzJob job = quartzJobMapper.selectById(jobId);
        if(JobState.RUNNING.getValue().equals(job.getState())){
            throw new ServiceException("创建定时任务失败！");
        }
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>)Class.forName(job.getJobClassName());
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(job.getId().toString())
                    .usingJobData(JobConstants.JOB_PARAM_KEY,job.getParameter())
                    .build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getId().toString())
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
            job.setState(JobState.RUNNING.getValue());
            quartzJobMapper.updateById(job);
        } catch (SchedulerException e) {
            log.error(null, e);
            throw new ServiceException("创建定时任务失败");
        } catch (Exception e){
            log.error(null, e);
            throw new ServiceException("后台找不到该类名任务");
        }

    }

    /**
     * 暂停任务
     * @param jobKey
     */
    @Override
    public void pauseJob(Long jobKey) {
        QuartzJob job = quartzJobMapper.selectById(jobKey);
        if(JobState.PAUSE.getValue().equals(job.getState())){
            throw new ServiceException("暂停定时任务失败！");
        }
        try {
            scheduler.pauseJob(JobKey.jobKey(jobKey.toString()));
            job.setState(JobState.PAUSE.getValue());
            quartzJobMapper.updateById(job);
        } catch (SchedulerException e) {
            log.error(null, e);
            throw new ServiceException("暂停定时任务失败");
        }
    }

    @Override
    public void resumeJob(Long jobKey) {
        QuartzJob job = quartzJobMapper.selectById(jobKey);
        if(JobState.RUNNING.getValue().equals(job.getState())){
            throw new ServiceException("恢复定时任务失败！");
        }
        try {
            scheduler.resumeJob(JobKey.jobKey(jobKey.toString()));
            job.setState(JobState.RUNNING.getValue());
            quartzJobMapper.updateById(job);
        } catch (SchedulerException e) {
            log.error(null, e);
            throw new ServiceException("恢复定时任务失败");
        }
    }

    @Override
    public void stopJob(Long jobKey){
        QuartzJob job = quartzJobMapper.selectById(jobKey);
        if(JobState.STOPED.getValue().equals(job.getState())){
            throw new ServiceException("停止定时任务失败！");
        }
        try {
            scheduler.deleteJob(JobKey.jobKey(jobKey.toString()));
            job.setState(JobState.STOPED.getValue());
            quartzJobMapper.updateById(job);
        } catch (SchedulerException e) {
            log.error(null, e);
            throw new ServiceException("停止定时任务失败");
        }
    }

    @Override
    public void removeJob(Long id) {
        quartzJobMapper.deleteById(id);
    }

    private void start() throws SchedulerException {
        scheduler.start();
    }

    private boolean isStarted() throws SchedulerException {
        return scheduler.isStarted();
    }

    private void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }
}
