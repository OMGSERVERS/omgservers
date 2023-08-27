package com.omgservers.module.admin.impl.service.adminService.impl;

import com.omgservers.dto.admin.CollectLogsAdminRequest;
import com.omgservers.dto.admin.CollectLogsAdminResponse;
import com.omgservers.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.dto.admin.CreateTenantAdminRequest;
import com.omgservers.dto.admin.CreateTenantAdminResponse;
import com.omgservers.dto.admin.GenerateIdAdminResponse;
import com.omgservers.dto.admin.PingServerAdminResponse;
import com.omgservers.module.admin.impl.service.adminService.AdminService;
import com.omgservers.module.admin.impl.service.adminService.impl.method.collectLogs.CollectLogsMethod;
import com.omgservers.module.admin.impl.service.adminService.impl.method.createNewDeveloper.CreateDeveloperMethod;
import com.omgservers.module.admin.impl.service.adminService.impl.method.createNewTenant.CreateTenantMethod;
import com.omgservers.module.admin.impl.service.adminService.impl.method.generateId.GenerateIdMethod;
import com.omgservers.module.admin.impl.service.adminService.impl.method.pingServer.PingServerMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminServiceImpl implements AdminService {

    final CreateDeveloperMethod createDeveloperAdminMethod;
    final CreateTenantMethod createTenantAdminMethod;
    final CollectLogsMethod collectLogsMethod;
    final PingServerMethod pingServerMethod;
    final GenerateIdMethod generateIdMethod;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return generateIdMethod.getId();
    }

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return createTenantAdminMethod.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return createDeveloperAdminMethod.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return collectLogsMethod.collectLogs(request);
    }
}
