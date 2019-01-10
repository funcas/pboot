package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.sys.entity.DictionaryCategory;
import com.funcas.pboot.module.sys.service.ISystemVariableService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class DictionaryCategoryControllerTest extends RestTestCaseSupport {

    @Autowired
    private ISystemVariableService systemVariableService;

    @Test
    public void getDictCategorys() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/dict-categories")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveDictCategory() throws Exception {
        DictionaryCategory category = new DictionaryCategory();
        category.setName("test");
        category.setCode("TEST");
        category.setRemark("");
        MvcResult mvcResult = mockMvc.perform(get("/sys/dict-category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(category))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteCategoryById() throws Exception {
        DictionaryCategory category = new DictionaryCategory();
        category.setName("test");
        category.setCode("TEST");
        category.setRemark("");
        systemVariableService.saveDictionaryCategory(category);
        MvcResult mvcResult = mockMvc.perform(delete("/sys/dict-category/" + category.getId())
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }
}