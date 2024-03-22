package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl;

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
import com.omgservers.service.entrypoint.admin.impl.service.adminService.AdminService;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createDeveloper.CreateDeveloperMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createIndex.CreateIndexMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createServiceAccount.CreateServiceAccountMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createTenant.CreateTenantMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.findIndex.FindIndexMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.findServiceAccount.FindServiceAccountMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.generateId.GenerateIdMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.pingServer.PingServerMethod;
import com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.syncIndexMethod.SyncIndexMethod;
import io.quarkus.elytron.security.common.BcryptUtil;
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

    final CreateServiceAccountMethod createServiceAccountMethod;
    final FindServiceAccountMethod findServiceAccountMethod;
    final CreateDeveloperMethod createDeveloperMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final CreateIndexMethod createIndexMethod;
    final PingServerMethod pingServerMethod;
    final GenerateIdMethod generateIdMethod;
    final SyncIndexMethod syncIndexMethod;
    final FindIndexMethod findIndexMethod;

    @Override
    public Uni<PingServerAdminResponse> pingServer() {
        return pingServerMethod.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return generateIdMethod.getId();
    }

    @Override
    public Uni<BcryptHashAdminResponse> bcryptHash(@Valid final BcryptHashAdminRequest request) {
        return Uni.createFrom().item(BcryptUtil.bcryptHash(request.getValue()))
                .map(BcryptHashAdminResponse::new);
    }

    @Override
    public Uni<FindIndexAdminResponse> findIndex(@Valid final FindIndexAdminRequest request) {
        return findIndexMethod.findIndex(request);
    }

    @Override
    public Uni<CreateIndexAdminResponse> createIndex(@Valid final CreateIndexAdminRequest request) {
        return createIndexMethod.createIndex(request);
    }

    @Override
    public Uni<SyncIndexAdminResponse> syncIndex(@Valid final SyncIndexAdminRequest request) {
        return syncIndexMethod.syncIndex(request);
    }

    @Override
    public Uni<FindServiceAccountAdminResponse> findServiceAccount(
            @Valid final FindServiceAccountAdminRequest request) {
        return findServiceAccountMethod.findServiceAccount(request);
    }

    @Override
    public Uni<CreateServiceAccountAdminResponse> createServiceAccount(
            @Valid final CreateServiceAccountAdminRequest request) {
        return createServiceAccountMethod.createServiceAccount(request);
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
}
