package com.omgservers.service.module.admin.impl.service.webService.impl;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.admin.FindServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.FindServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.service.module.admin.impl.service.adminService.AdminService;
import com.omgservers.service.module.admin.impl.service.webService.WebService;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final SystemModule systemModule;
    final AdminService adminService;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return adminService.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return adminService.generateId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(final BcryptHashAdminRequest request) {
        return adminService.bcryptHash(request);
    }

    @Override
    public Uni<FindIndexAdminResponse> findIndex(final FindIndexAdminRequest request) {
        return adminService.findIndex(request);
    }

    @Override
    public Uni<CreateIndexAdminResponse> createIndex(final CreateIndexAdminRequest request) {
        return adminService.createIndex(request);
    }

    @Override
    public Uni<SyncIndexAdminResponse> syncIndex(final SyncIndexAdminRequest request) {
        return adminService.syncIndex(request);
    }

    @Override
    public Uni<FindServiceAccountAdminResponse> findServiceAccount(final FindServiceAccountAdminRequest request) {
        return adminService.findServiceAccount(request);
    }

    @Override
    public Uni<CreateServiceAccountAdminResponse> createServiceAccount(final CreateServiceAccountAdminRequest request) {
        return adminService.createServiceAccount(request);
    }

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(final CreateTenantAdminRequest request) {
        return adminService.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantAdminResponse> deleteTenant(final DeleteTenantAdminRequest request) {
        return adminService.deleteTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(final CreateDeveloperAdminRequest request) {
        return adminService.createDeveloper(request);
    }
}
