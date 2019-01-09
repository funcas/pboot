package com.funcas.pboot.module.upms.service.impl;

import com.funcas.pboot.module.upms.entity.Group;
import com.funcas.pboot.module.upms.entity.User;
import com.funcas.pboot.module.upms.enumeration.DataScopeType;
import com.funcas.pboot.module.util.VariableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月12日
 */
@Slf4j
public abstract class BaseBizService {

    private static final String UNIT_COLUMN = "unit_id";
    private static final String USER_COLUMN = "creator_id";

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
        for(Group group : user.getGroups()) {
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
        String orgWhere = bizAlias + unitColumn + " = id";
        String userWhere = bizAlias + userColumn + " = " + user.getId();
        if(DataScopeType.SCOPE_ALL.getValue().equals(dataScopeValue)){
            return "";
        }else if (DataScopeType.SCOPE_ORG_CHILDREN.getValue().equals(dataScopeValue)){
            // 包括本公司下的部门 （type=1:公司；type=2：部门）
            sqlString.append(" EXISTS (SELECT 1 FROM tb_unit");
            sqlString.append(" WHERE org_code LIKE '").append(user.getOrganization().getOrgCode()).append("%'");
            sqlString.append(" AND ").append(orgWhere).append(")");
        } else if (DataScopeType.SCOPE_ORG.getValue().equals(dataScopeValue)){
            sqlString.append(" EXISTS (SELECT 1 FROM tb_unit");
            sqlString.append(" WHERE id = ").append(user.getOrganization().getId());
            sqlString.append(" AND ").append(orgWhere).append(")");
        } else if (DataScopeType.SCOPE_CUSTOM.getValue().equals(dataScopeValue)){
            sqlString.append(" EXISTS (SELECT 1 FROM tb_group_unit, tb_unit");
            sqlString.append(" WHERE tb_group_unit.unit_id = tb_unit.id");
            sqlString.append(" AND tb_group_unit.group_id = ").append(groupId);
            sqlString.append(" AND tb_unit.id = ").append(bizAlias).append(unitColumn).append(")");
        } else if (DataScopeType.SCOPE_NONE.getValue().equals(dataScopeValue)){
            sqlString.append(" 1 = 0 ");
        } else if (DataScopeType.SCOPE_SELF.getValue().equals(dataScopeValue)){
            sqlString.append(userWhere);
        }
        return sqlString.toString();
    }
}
