package com.funcas.pboot.module;

import com.alibaba.fastjson.JSONObject;
import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.util.FastJsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class RestTestCaseSupport extends ServiceTestCaseSupport{

    protected MockMvc mockMvc;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    protected String accessToken;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();

        this.accessToken = obtainAccessToken();
    }

    protected JSONObject processMvcResult(String result) {
        ApiResult ret = FastJsonUtil.getBean(result, ApiResult.class);
        return (JSONObject) ret.getResult();
    }

    protected void assertCode(String result) {
        ApiResult ret = FastJsonUtil.getBean(result, ApiResult.class);
        Assert.assertEquals(ret.getRetCode(), "000");
    }


    private String obtainAccessToken() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "admin");
        params.add("password", "123456");

        ResultActions resultActions = null;
        try {
            resultActions = mockMvc.perform(post("/oauth/token")
                    .params(params)
                    .with(httpBasic("app", "app"))
                    .accept("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"));
            String resultStr = resultActions.andReturn().getResponse().getContentAsString();
            Map<String, Object> result = FastJsonUtil.getBean(resultStr, Map.class);
            return (String)result.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
