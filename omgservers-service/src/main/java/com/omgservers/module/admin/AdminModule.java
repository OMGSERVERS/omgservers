package com.omgservers.module.admin;

import com.omgservers.module.admin.impl.service.adminService.AdminService;

public interface AdminModule {

    AdminService getAdminService();
}