package com.omgservers.service.module.admin.impl.service.adminService.impl;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.service.module.admin.impl.service.adminService.AdminService;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.collectLogs.CollectLogsMethod;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.createDeveloper.CreateDeveloperMethod;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.createTenant.CreateTenantMethod;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.generateId.GenerateIdMethod;
import com.omgservers.service.module.admin.impl.service.adminService.impl.method.pingServer.PingServerMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AdminServiceImpl implements AdminService {

    final CreateDeveloperMethod createDeveloperMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
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
    public Uni<CreateTenantAdminResponse> createTenant(@Valid final CreateTenantAdminRequest request) {
        return createTenantMethod.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantAdminResponse> deleteTenant(@Valid final DeleteTenantAdminRequest request) {
        return deleteTenantMethod.deleteTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(@Valid final CreateDeveloperAdminRequest request) {
        return createDeveloperMethod.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(@Valid final CollectLogsAdminRequest request) {
        return collectLogsMethod.collectLogs(request);
    }
}
