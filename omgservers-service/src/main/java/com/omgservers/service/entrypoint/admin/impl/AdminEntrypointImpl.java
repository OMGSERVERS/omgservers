package com.omgservers.service.entrypoint.admin.impl;

import com.omgservers.service.entrypoint.admin.AdminEntrypoint;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminEntrypointImpl implements AdminEntrypoint {

    final AdminService adminService;

    public AdminService getAdminService() {
        return adminService;
    }
}
