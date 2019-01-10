package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.sys.entity.QuartzJob;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class JobControllerTest extends RestTestCaseSupport {

    @Test
    public void getJobLists() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/jobs")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveJob() throws Exception {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setJobClassName("***.**.**.*");
        quartzJob.setCronExpression("0/10 0 0 0 0 *");
        quartzJob.setState(State.ENABLE.getValue());
        MvcResult mvcResult = mockMvc.perform(post("/sys/jobs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(quartzJob))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteJob() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/sys/jobs")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }
}