package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.upms.entity.Resource;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class ResourceControllerTest extends RestTestCaseSupport {


    @Test
    public void save() throws Exception {
        Resource resource = new Resource();
        resource.setSort(1);
        resource.setName("test");
        resource.setPermission("demo:demo");
        resource.setValue("/test");
        resource.setType(1);
        MvcResult mvcResult = mockMvc.perform(post("/sys/resource")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(resource))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testDelete() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete("/sys/unit")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }
}