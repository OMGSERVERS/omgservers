package com.omgservers.service.module.admin.impl.service.webService.impl.adminApi;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
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
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.admin.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminApiImpl implements AdminApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<PingServerAdminResponse> pingServer() {
        return webService.pingServer();
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GenerateIdAdminResponse> generateId() {
        return webService.generateId();
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::bcryptHash);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<FindIndexAdminResponse> findIndex(final FindIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateIndexAdminResponse> createIndex(final CreateIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createIndex);
    }

    @Override
    public Uni<SyncIndexAdminResponse> syncIndex(final SyncIndexAdminRequest request) {
        return null;
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<FindServiceAccountAdminResponse> findServiceAccount(final FindServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateServiceAccountAdminResponse> createServiceAccount(final CreateServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createServiceAccount);
    }

    @Override
    public Uni<SyncServiceAccountAdminResponse> syncServiceAccount(SyncServiceAccountAdminRequest request) {
        return null;
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateTenantAdminResponse> createTenant(final CreateTenantAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateDeveloperAdminResponse> createDeveloper(final CreateDeveloperAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createDeveloper);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CollectLogsAdminResponse> collectLogs(final CollectLogsAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::collectLogs);
    }
}
