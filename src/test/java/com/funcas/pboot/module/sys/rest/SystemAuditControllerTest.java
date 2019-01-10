package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.module.RestTestCaseSupport;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class SystemAuditControllerTest extends RestTestCaseSupport {

    @Test
    public void findAuditPaged() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/sys/audits")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }
}