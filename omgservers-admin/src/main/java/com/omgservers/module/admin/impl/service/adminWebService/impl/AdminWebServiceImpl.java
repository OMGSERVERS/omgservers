package com.omgservers.module.admin.impl.service.adminWebService.impl;

import com.omgservers.module.admin.impl.service.adminService.AdminService;
import com.omgservers.module.admin.impl.service.adminWebService.AdminWebService;
import com.omgservers.module.internal.InternalModule;
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
import com.omgservers.dto.internal.DeleteIndexRequest;
import com.omgservers.dto.internal.DeleteServiceAccountRequest;
import com.omgservers.dto.internal.GetIndexRequest;
import com.omgservers.dto.internal.GetIndexResponse;
import com.omgservers.dto.internal.GetServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountResponse;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AdminWebServiceImpl implements AdminWebService {

    final InternalModule internalModule;
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
    public Uni<GetIndexAdminResponse> getIndex(GetIndexAdminRequest request) {
        final var name = request.getName();
        return internalModule.getIndexService().getIndex(new GetIndexRequest(name))
                .map(GetIndexResponse::getIndex)
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
                .getServiceAccount(new GetServiceAccountRequest(username))
                .map(GetServiceAccountResponse::getServiceAccount)
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
                .deleteServiceAccount(new DeleteServiceAccountRequest(id));
    }

    @Override
    public Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request) {
        return adminService.createTenant(request);
    }

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request) {
        return adminService.createDeveloper(request);
    }

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request) {
        return adminService.collectLogs(request);
    }
}
