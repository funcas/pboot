package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.sys.entity.DataDictionary;
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
public class DataDictionaryControllerTest extends RestTestCaseSupport {

    @Test
    public void getDicts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/dicts")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveDict() throws Exception {
        DataDictionary dict = new DataDictionary();
        dict.setName("test");
        dict.setType("I");
        dict.setValue("1");
        dict.setFkCategoryId(1L);
        MvcResult mvcResult = mockMvc.perform(post("/sys/dict")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(dict))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void delDict() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/sys/dict/1")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }
}