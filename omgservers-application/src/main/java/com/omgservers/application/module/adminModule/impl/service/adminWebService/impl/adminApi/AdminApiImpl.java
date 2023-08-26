package com.omgservers.application.module.adminModule.impl.service.adminWebService.impl.adminApi;

import com.omgservers.application.module.adminModule.impl.service.adminWebService.AdminWebService;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import com.omgservers.dto.adminModule.CreateTenantAdminRequest;
import com.omgservers.dto.adminModule.CreateTenantAdminResponse;
import com.omgservers.dto.adminModule.DeleteIndexAdminRequest;
import com.omgservers.dto.adminModule.DeleteServiceAccountAdminRequest;
import com.omgservers.dto.adminModule.GenerateIdAdminResponse;
import com.omgservers.dto.adminModule.GetIndexAdminRequest;
import com.omgservers.dto.adminModule.GetIndexAdminResponse;
import com.omgservers.dto.adminModule.GetServiceAccountAdminRequest;
import com.omgservers.dto.adminModule.GetServiceAccountAdminResponse;
import com.omgservers.dto.adminModule.PingServerAdminResponse;
import com.omgservers.dto.adminModule.SyncIndexAdminRequest;
import com.omgservers.dto.adminModule.SyncServiceAccountAdminRequest;
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

    final HandleApiRequestOperation handleApiRequestOperation;
    final AdminWebService adminWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<PingServerAdminResponse> pingServer() {
        return adminWebService.pingServer();
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GenerateIdAdminResponse> generateId() {
        return adminWebService.generateId();
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GetIndexAdminResponse> getIndex(final GetIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::getIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncIndex(final SyncIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteIndex(final DeleteIndexAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::deleteIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GetServiceAccountAdminResponse> getServiceAccount(final GetServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::getServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncServiceAccount(final SyncServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteServiceAccount(final DeleteServiceAccountAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::deleteServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::createTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::createDeveloper);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::collectLogs);
    }
}
