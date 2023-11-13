package com.omgservers.service.module.admin.impl.service.webService.impl;

import com.omgservers.model.dto.admin.CollectLogsAdminRequest;
import com.omgservers.model.dto.admin.CollectLogsAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteIndexAdminRequest;
import com.omgservers.model.dto.admin.DeleteIndexAdminResponse;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.DeleteServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.admin.FindServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.FindServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.GetServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.GetServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminResponse;
import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.service.module.admin.impl.service.adminService.AdminService;
import com.omgservers.service.module.admin.impl.service.webService.WebService;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
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
    public Uni<FindIndexAdminResponse> findIndex(@Valid final FindIndexAdminRequest request) {
        final var name = request.getName();
        return systemModule.getIndexService().findIndex(new FindIndexRequest(name))
                .map(FindIndexResponse::getIndex)
                .map(FindIndexAdminResponse::new);
    }

    @Override
    public Uni<SyncIndexAdminResponse> syncIndex(@Valid final SyncIndexAdminRequest request) {
        final var index = request.getIndex();
        return systemModule.getIndexService().syncIndex(new SyncIndexRequest(index))
                .map(SyncIndexResponse::getCreated)
                .map(SyncIndexAdminResponse::new);
    }

    @Override
    public Uni<DeleteIndexAdminResponse> deleteIndex(@Valid final DeleteIndexAdminRequest request) {
        final var id = request.getId();
        return systemModule.getIndexService().deleteIndex(new DeleteIndexRequest(id))
                .map(DeleteIndexResponse::getDeleted)
                .map(DeleteIndexAdminResponse::new);
    }

    @Override
    public Uni<GetServiceAccountAdminResponse> getServiceAccount(@Valid final GetServiceAccountAdminRequest request) {
        final var id = request.getId();
        return systemModule.getServiceAccountService().getServiceAccount(new GetServiceAccountRequest(id))
                .map(GetServiceAccountResponse::getServiceAccount)
                .map(GetServiceAccountAdminResponse::new);
    }

    @Override
    public Uni<FindServiceAccountAdminResponse> findServiceAccount(
            @Valid final FindServiceAccountAdminRequest request) {
        final var username = request.getUsername();
        return systemModule.getServiceAccountService().findServiceAccount(new FindServiceAccountRequest(username))
                .map(FindServiceAccountResponse::getServiceAccount)
                .map(FindServiceAccountAdminResponse::new);
    }

    @Override
    public Uni<SyncServiceAccountAdminResponse> syncServiceAccount(
            @Valid final SyncServiceAccountAdminRequest request) {
        final var serviceAccount = request.getServiceAccount();
        return systemModule.getServiceAccountService().syncServiceAccount(new SyncServiceAccountRequest(serviceAccount))
                .map(SyncServiceAccountResponse::getCreated)
                .map(SyncServiceAccountAdminResponse::new);
    }

    @Override
    public Uni<DeleteServiceAccountAdminResponse> deleteServiceAccount(
            @Valid final DeleteServiceAccountAdminRequest request) {
        final var id = request.getId();
        return systemModule.getServiceAccountService().deleteServiceAccount(new DeleteServiceAccountRequest(id))
                .map(DeleteServiceAccountResponse::getDeleted)
                .map(DeleteServiceAccountAdminResponse::new);
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

    @Override
    public Uni<CollectLogsAdminResponse> collectLogs(final CollectLogsAdminRequest request) {
        return adminService.collectLogs(request);
    }
}
