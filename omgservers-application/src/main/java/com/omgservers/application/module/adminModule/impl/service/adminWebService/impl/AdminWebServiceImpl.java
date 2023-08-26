package com.omgservers.application.module.adminModule.impl.service.adminWebService.impl;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.AdminHelpService;
import com.omgservers.application.module.adminModule.impl.service.adminWebService.AdminWebService;
import com.omgservers.module.internal.InternalModule;
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
import com.omgservers.dto.internalModule.DeleteIndexRequest;
import com.omgservers.dto.internalModule.DeleteServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetIndexRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import com.omgservers.dto.internalModule.GetServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.GetServiceAccountHelpResponse;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
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
    public Uni<PingServerAdminResponse> pingServer() {
        return adminHelpService.pingServer();
    }

    @Override
    public Uni<GenerateIdAdminResponse> generateId() {
        return adminHelpService.generateId();
    }

    @Override
    public Uni<GetIndexAdminResponse> getIndex(GetIndexAdminRequest request) {
        final var name = request.getName();
        return internalModule.getIndexService().getIndex(new GetIndexRequest(name))
                .map(GetIndexHelpResponse::getIndex)
                .map(GetIndexAdminResponse::new);
    }

    @Override
    public Uni<Void> syncIndex(SyncIndexAdminRequest request) {
        final var index = request.getIndex();
        return internalModule.getIndexService().syncIndex(new SyncIndexRequest(index));
    }

    @Override
    public Uni<Void> deleteIndex(DeleteIndexAdminRequest request) {
        final var id = request.getId();
        return internalModule.getIndexService().deleteIndex(new DeleteIndexRequest(id));
    }

    @Override
    public Uni<GetServiceAccountAdminResponse> getServiceAccount(GetServiceAccountAdminRequest request) {
        final var username = request.getUsername();
        return internalModule.getServiceAccountService()
                .getServiceAccount(new GetServiceAccountHelpRequest(username))
                .map(GetServiceAccountHelpResponse::getServiceAccount)
                .map(GetServiceAccountAdminResponse::new);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountAdminRequest request) {
        final var serviceAccount = request.getServiceAccount();
        return internalModule.getServiceAccountService()
                .syncServiceAccount(new SyncServiceAccountRequest(serviceAccount));
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountAdminRequest request) {
        final var id = request.getId();
        return internalModule.getServiceAccountService()
                .deleteServiceAccount(new DeleteServiceAccountHelpRequest(id));
    }

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return adminHelpService.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return adminHelpService.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return adminHelpService.collectLogs(request);
    }
}
