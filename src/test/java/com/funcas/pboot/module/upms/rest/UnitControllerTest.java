package com.funcas.pboot.module.upms.rest;

import com.funcas.pboot.common.enumeration.entity.DelFlag;
import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.RestTestCaseSupport;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.service.IUnitService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月10日
 */
public class UnitControllerTest extends RestTestCaseSupport {

    @Autowired
    private IUnitService unitService;

    @Test
    public void getOrgByParentId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/unit/tree/1")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getAllUnits() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/units")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveUnit() throws Exception {
        Unit unit = new Unit();
        unit.setName("test");
        unit.setParentId(1L);
        unit.setState(State.ENABLE.getValue());
        unit.setSort(100);
        unit.setDelFlag(DelFlag.NORMAL.getValue());
        MvcResult mvcResult = mockMvc.perform(post("/sys/unit")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(FastJsonUtil.toJson(unit))
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteUnit() throws Exception {
        Unit unit = new Unit();
        unit.setName("test");
        unit.setParentId(1L);
        unit.setState(State.ENABLE.getValue());
        unit.setSort(100);
        unit.setDelFlag(DelFlag.NORMAL.getValue());
        unitService.saveUnit(unit);
        MvcResult mvcResult = mockMvc.perform(delete("/sys/unit/" + unit.getId())
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getGroupUnits() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sys/unit/group/1")
                .header("Authorization", "bearer " + this.accessToken))
                .andExpect(status().isOk()).andReturn();
        assertCode(mvcResult.getResponse().getContentAsString());
    }

}