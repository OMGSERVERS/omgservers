package com.omgservers.application.module.adminModule.impl.service.adminWebService.impl;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.AdminHelpService;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.PingServerHelpResponse;
import com.omgservers.application.module.adminModule.impl.service.adminWebService.AdminWebService;
import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.GetServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.response.GetServiceAccountHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AdminWebServiceImpl implements AdminWebService {

    final InternalModule internalModule;
    final AdminHelpService adminHelpService;

    @Override
    public Uni<PingServerHelpResponse> pingServer() {
        return adminHelpService.pingServer();
    }

    @Override
    public Uni<GetIndexHelpResponse> getIndex(GetIndexHelpRequest request) {
        return internalModule.getIndexHelpService().getIndex(request);
    }

    @Override
    public Uni<Void> syncIndex(SyncIndexHelpRequest request) {
        return internalModule.getIndexHelpService().syncIndex(request);
    }

    @Override
    public Uni<Void> deleteIndex(DeleteIndexHelpRequest request) {
        return internalModule.getIndexHelpService().deleteIndex(request);
    }

    @Override
    public Uni<GetServiceAccountHelpResponse> getServiceAccount(GetServiceAccountHelpRequest request) {
        return internalModule.getServiceAccountHelpService().getServiceAccount(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request) {
        return internalModule.getServiceAccountHelpService().syncServiceAccount(request);
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request) {
        return internalModule.getServiceAccountHelpService().deleteServiceAccount(request);
    }

    @Override
    public Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request) {
        return adminHelpService.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperHelpResponse> createDeveloper(CreateDeveloperHelpRequest request) {
        return adminHelpService.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request) {
        return adminHelpService.collectLogs(request);
    }
}
