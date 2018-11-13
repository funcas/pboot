package com.funcas.pboot.module.sys.rest;

import com.funcas.pboot.common.ApiResult;
import com.funcas.pboot.common.PageRequest;
import com.funcas.pboot.common.base.BaseController;
import com.funcas.pboot.module.sys.service.ISystemAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 操作日志管理
 * @author funcas
 * @version 1.0
 * @date 2018年11月06日
 */
@RestController
@RequestMapping("/sys")
public class SystemAuditController extends BaseController {

    @Autowired
    private ISystemAuditService auditService;

    /**
     * 分页查询操作日志
     * @param pageRequest
     * @param filter
     * @return
     */
    @GetMapping("/audits")
    @PreAuthorize("hasAuthority('audit:list')")
    public ApiResult findAuditPaged(PageRequest pageRequest, Map<String,Object> filter){
        return success(auditService.findOperatingRecordPaged(pageRequest, filter));
    }


}
