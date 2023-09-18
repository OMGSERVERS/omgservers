package com.omgservers.module.admin.impl.service.webService.impl.adminApi;

import com.omgservers.module.admin.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.admin.CollectLogsAdminRequest;
import com.omgservers.dto.admin.CollectLogsAdminResponse;
import com.omgservers.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.dto.admin.CreateTenantAdminRequest;
import com.omgservers.dto.admin.CreateTenantAdminResponse;
import com.omgservers.dto.admin.DeleteIndexAdminRequest;
import com.omgservers.dto.admin.DeleteServiceAccountAdminRequest;
import com.omgservers.dto.admin.GenerateIdAdminResponse;
import com.omgservers.dto.admin.GetIndexAdminRequest;
import com.omgservers.dto.admin.GetIndexAdminResponse;
import com.omgservers.dto.admin.GetServiceAccountAdminRequest;
import com.omgservers.dto.admin.GetServiceAccountAdminResponse;
import com.omgservers.dto.admin.PingServerAdminResponse;
import com.omgservers.dto.admin.SyncIndexAdminRequest;
import com.omgservers.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.internalRole.InternalRoleEnum;
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
    public Uni<GetIndexAdminResponse> getIndex(final GetIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncIndex(final SyncIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteIndex(final DeleteIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GetServiceAccountAdminResponse> getServiceAccount(final GetServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncServiceAccount(final SyncServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteServiceAccount(final DeleteServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createDeveloper);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::collectLogs);
    }
}