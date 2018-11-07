package com.funcas.pboot.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.module.sys.entity.QuartzJob;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
public interface IJobService {

    IPage<QuartzJob> find(PageRequest pageRequest, Map<String,Object> filter);

    void saveJob(QuartzJob job);

    void removeJob(Long id);

    public void startJob(Long jobId);

    public void pauseJob(Long jobId);

    public void resumeJob(Long jobId);

    public void stopJob(Long jobId);

}
