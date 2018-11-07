package com.funcas.pboot.module.sys.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.sys.annotation.OperatingAudit;
import com.funcas.pboot.module.sys.entity.QuartzJob;
import com.funcas.pboot.module.sys.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月02日
 */
@RestController
@RequestMapping("/sys")
public class JobController extends BaseController {

    private final IJobService jobService;

    @Autowired
    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ApiResult getJobLists(PageRequest pageRequest, @RequestParam Map<String,Object> filter){
        IPage<QuartzJob> jobIPage = jobService.find(pageRequest, filter);
        return success(jobIPage);
    }

    @PostMapping("/job")
    @OperatingAudit(value = "定时任务", function = "保存定时器")
    public ApiResult saveJob(@RequestBody QuartzJob entity){
        jobService.saveJob(entity);
        return success(entity);
    }

    @DeleteMapping("/job/{id}")
    public ApiResult deleteJob(@PathVariable("id") Long id){
        jobService.removeJob(id);
        return success(id);
    }

    @GetMapping("/job-start/{id}")
    public ApiResult startJob(@PathVariable("id") Long id){
        jobService.startJob(id);
        return success(id);
    }

    @GetMapping("job-pause/{id}")
    public Object pauseJob(@PathVariable("id") Long id){
        jobService.pauseJob(id);
        return success(id);
    }

    @GetMapping("/job-resume/{id}")
    public Object resumeJob(@PathVariable("id") Long id){
        jobService.resumeJob(id);
        return success(id);
    }

    @GetMapping("/job-stop/{id}")
    public Object stopJob(@PathVariable("id") Long id){
        jobService.stopJob(id);
        return success(id);
    }
}
