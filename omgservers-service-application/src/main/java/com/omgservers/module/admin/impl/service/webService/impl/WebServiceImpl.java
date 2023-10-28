package com.omgservers.module.admin.impl.service.webService.impl;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteIndexAdminRequest;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.GetIndexAdminRequest;
import com.omgservers.model.dto.admin.GetIndexAdminResponse;
import com.omgservers.model.dto.admin.GetServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GetServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.dto.internal.DeleteIndexRequest;
import com.omgservers.model.dto.internal.DeleteServiceAccountRequest;
import com.omgservers.model.dto.internal.GetIndexRequest;
import com.omgservers.model.dto.internal.GetIndexResponse;
import com.omgservers.model.dto.internal.GetServiceAccountRequest;
import com.omgservers.model.dto.internal.GetServiceAccountResponse;
import com.omgservers.model.dto.internal.SyncIndexRequest;
import com.omgservers.model.dto.internal.SyncServiceAccountRequest;
import com.omgservers.module.admin.impl.service.adminService.AdminService;
import com.omgservers.module.admin.impl.service.webService.WebService;
import com.omgservers.module.system.SystemModule;
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
    public Uni<GetIndexAdminResponse> getIndex(GetIndexAdminRequest request) {
        final var name = request.getName();
        return systemModule.getIndexService().getIndex(new GetIndexRequest(name))
                .map(GetIndexResponse::getIndex)
                .map(GetIndexAdminResponse::new);
    }

    @Override
    public Uni<Void> syncIndex(SyncIndexAdminRequest request) {
        final var index = request.getIndex();
        return systemModule.getIndexService().syncIndex(new SyncIndexRequest(index));
    }

    @Override
    public Uni<Void> deleteIndex(DeleteIndexAdminRequest request) {
        final var id = request.getId();
        return systemModule.getIndexService().deleteIndex(new DeleteIndexRequest(id));
    }

    @Override
    public Uni<GetServiceAccountAdminResponse> getServiceAccount(GetServiceAccountAdminRequest request) {
        final var username = request.getUsername();
        return systemModule.getServiceAccountService()
                .getServiceAccount(new GetServiceAccountRequest(username))
                .map(GetServiceAccountResponse::getServiceAccount)
                .map(GetServiceAccountAdminResponse::new);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountAdminRequest request) {
        final var serviceAccount = request.getServiceAccount();
        return systemModule.getServiceAccountService()
                .syncServiceAccount(new SyncServiceAccountRequest(serviceAccount));
    }

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountAdminRequest request) {
        final var username = request.getUsername();
        return systemModule.getServiceAccountService()
                .deleteServiceAccount(new DeleteServiceAccountRequest(username));
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
