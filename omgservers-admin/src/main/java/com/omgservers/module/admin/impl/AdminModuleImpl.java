package com.omgservers.module.admin.impl;

import com.omgservers.module.admin.AdminModule;
import com.omgservers.module.admin.impl.service.adminService.AdminService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminModuleImpl implements AdminModule {

    final AdminService adminService;

    @Override
    public AdminService getAdminService() {
        return adminService;
    }
}
