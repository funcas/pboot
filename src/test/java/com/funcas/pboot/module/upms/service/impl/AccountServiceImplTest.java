package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.common.enumeration.entity.State;
import com.funcas.pboot.module.ServiceTestCaseSupport;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.service.IAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author funcas
 * @version 1.0
 * @date 2019年01月09日
 */
public class AccountServiceImplTest extends ServiceTestCaseSupport {

    private static final String TABLE_NAME = "tb_user";

    @Autowired
    private IAccountService accountService;

    private User generateUser(){
        User entity = new User();
        entity.setPassword("****");
        entity.setUsername("fsssss");
        entity.setNickname("ssssss");
        entity.setEmail("aaa@2121.com");
        entity.setState(State.ENABLE.getValue());
        return entity;
    }

    @Test
    public void getUser() {
        User user = accountService.getUser(5L);
        Assert.assertEquals("admin", user.getUsername());
    }

    @Test
    @Transactional
    public void saveUser() {
        int before = countRowsInTable(TABLE_NAME);
        accountService.saveUser(this.generateUser());
        int after = countRowsInTable(TABLE_NAME);
        Assert.assertEquals(before + 1, after);

    }

    @Test
    @Transactional
    public void deleteUser() {
        User u = this.generateUser();
        accountService.saveUser(u);
        int before = countRowsInTable(TABLE_NAME);
        accountService.deleteUser(u);
        int after = countRowsInTable(TABLE_NAME);
        Assert.assertEquals(before - 1, after);

    }
}