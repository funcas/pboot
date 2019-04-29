package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.module.sys.entity.DataDictionary;
import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.entity.Unit;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.enumeration.DataScopeType;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月12日
 */
@Slf4j
public abstract class BaseBizService {

    protected static final String UNIT_COLUMN = "unit_id";
    protected static final String USER_COLUMN = "creator_id";

    protected static final String DS_FILTER = "ds";


    protected String dataScopeFilter(String bizAlias) {
        return this.dataScopeFilter(bizAlias, UNIT_COLUMN, USER_COLUMN);
    }

    /**
     * 获取数据权限sql片段，注意请不要对外开放输入，防止引起sql注入漏洞
     * @param bizAlias
     * @param unitColumn
     * @param userColumn
     * @return
     */
    protected String dataScopeFilter(String bizAlias, String unitColumn, String userColumn) {
        User user = VariableUtils.getPrincipal().getBaseUser();
        Integer dataScopeValue = 99;
        Long groupId = 0L;
        if (user == null){
            return "1 = 0";
        }
        for(Object obj : user.getGroups()) {
            Group group = (Group)obj;
            Integer dsv = group.getDataScope();
            if(dsv == 9) {
                dataScopeValue = dsv;
                groupId = group.getId();
                break;
            }else if(dsv < dataScopeValue){
                dataScopeValue = dsv;
                groupId = group.getId();
            }
        }

        StringBuilder sqlString = new StringBuilder();
        if(StringUtils.isNotEmpty(bizAlias)){
            bizAlias = bizAlias + ".";
        }
        String orgWhere = bizAlias + unitColumn + " = ooU.id";
        String userWhere = bizAlias + userColumn + " = " + user.getId();
        if(DataScopeType.SCOPE_ALL.getValue().equals(dataScopeValue)){
            return "";
        }else if (DataScopeType.SCOPE_ORG_CHILDREN.getValue().equals(dataScopeValue)){
            // 包括本公司下的部门
            sqlString.append(" EXISTS (SELECT 1 FROM tb_unit ooU");
            sqlString.append(" WHERE ooU.org_code LIKE '").append(((Unit)user.getUnit()).getOrgCode()).append("%'");
            sqlString.append(" AND ").append(orgWhere).append(")");
        } else if (DataScopeType.SCOPE_ORG.getValue().equals(dataScopeValue)){
            sqlString.append(" EXISTS (SELECT 1 FROM tb_unit ooU");
            sqlString.append(" WHERE ooU.id = ").append(((Unit)user.getUnit()).getId());
            sqlString.append(" AND ").append(orgWhere).append(")");
        } else if (DataScopeType.SCOPE_CUSTOM.getValue().equals(dataScopeValue)){
            sqlString.append(" EXISTS (SELECT 1 FROM tb_group_unit ooGu, tb_unit ooU");
            sqlString.append(" WHERE ooGu.unit_id = ooU.id");
            sqlString.append(" AND ooGu.group_id = ").append(groupId);
            sqlString.append(" AND ooU.id = ").append(bizAlias).append(unitColumn).append(")");
        } else if (DataScopeType.SCOPE_NONE.getValue().equals(dataScopeValue)){
            sqlString.append(" 1 = 0 ");
        } else if (DataScopeType.SCOPE_SELF.getValue().equals(dataScopeValue)){
            sqlString.append(userWhere);
        }
        return sqlString.toString();
    }


    protected void processDataScopeFilter(Map<String,Object> filter, String alias) {
        filter.put(DS_FILTER, this.dataScopeFilter(alias));
    }

    /**
     * 是否超级管理员
     * @return
     */
    protected boolean isSuperAdmin() {
        DataDictionary data = VariableUtils.getDataDictionary("SYSTEM_VAR_USER_ID");
        if (VariableUtils.getPrincipal() == null){
            return false;
        } else {
            return VariableUtils.cast(data.getValue(), Long.class)
                    .equals(VariableUtils.getPrincipal().getBaseUser().getId());
        }
    }
}
