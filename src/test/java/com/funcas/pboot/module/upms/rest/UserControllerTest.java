package com.funcas.pboot.module.upms.rest;

import com.alibaba.fastjson.JSONObject;
import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.service.IAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class UserControllerTest extends RestTestCaseSupport {

    @Autowired
    private IAccountService accountService;


    @Test
    public void getUserInfo() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/sys/userinfo")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        JSONObject jsonObject = processMvcResult(result.getResponse().getContentAsString());
        Assert.assertEquals("admin", jsonObject.getString("username"));
    }

    @Test
    public void getUserLists() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/users?pageNo=1&pageSize=10")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }


    @Test
    @Transactional
    public void saveUser() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setNickname("test");
        user.setPassword("123456");
        user.setState(State.ENABLE.getValue());
        MvcResult mvcResult = mockMvc.perform(post("/sys/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(user))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setNickname("test");
        user.setPassword("123456");
        user.setState(State.ENABLE.getValue());
        accountService.saveUser(user);
        MvcResult mvcResult = mockMvc.perform(delete("/sys/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(user))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

}