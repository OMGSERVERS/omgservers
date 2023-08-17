package com.omgservers.application.module.adminModule.impl.service.adminWebService.impl.adminApi;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminWebService.AdminWebService;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
    public Uni<PingServerHelpResponse> pingServer() {
        return adminWebService.pingServer();
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GetIndexHelpResponse> getIndex(final GetIndexHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::getIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncIndex(final SyncIndexHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteIndex(final DeleteIndexHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::deleteIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<GetServiceAccountHelpResponse> getServiceAccount(final GetServiceAccountHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::getServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> syncServiceAccount(final SyncServiceAccountHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<Void> deleteServiceAccount(final DeleteServiceAccountHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::deleteServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::createTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::createDeveloper);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.ADMIN})
    public Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, adminWebService::collectLogs);
    }
}
