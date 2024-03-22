package com.omgservers.service.entrypoint.admin;

import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;

public interface AdminEntrypoint {

    AdminService getAdminService();
}