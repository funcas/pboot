package com.funcas.pboot.module.upms.rest;

import com.alibaba.fastjson.JSONObject;
import com.funcas.pboot.module.RestTestCaseSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class UserControllerTest extends RestTestCaseSupport {


    @Test
    public void getUserInfo() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/sys/userinfo")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        JSONObject jsonObject = processMvcResult(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.getString("username"), "admin");
    }

    @Test
    public void getUserLists() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/users?pageNo=1&pageSize=10")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void modifyUser() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void deleteUserBatch() {
    }
}