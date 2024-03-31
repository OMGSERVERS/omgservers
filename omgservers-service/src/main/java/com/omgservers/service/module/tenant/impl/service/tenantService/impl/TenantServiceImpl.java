package com.omgservers.service.module.tenant.impl.service.tenantService.impl;

import com.omgservers.model.dto.tenant.DeleteTenantPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.deleteTenant.DeleteTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.deleteTenantPermission.DeleteTenantPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenant.GetTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenantDashboard.GetTenantDashboardMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.hasTenantPermission.HasTenantPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.syncTenant.SyncTenantMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.syncTenantPermission.SyncTenantPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantPermission.viewTenantPermissions.ViewTenantPermissionsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantServiceImpl implements TenantService {

    final DeleteTenantPermissionMethod deleteTenantPermissionMethod;
    final ViewTenantPermissionsMethod viewTenantPermissionsMethod;
    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final HasTenantPermissionMethod hasTenantPermissionMethod;
    final GetTenantDashboardMethod getTenantDashboard;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;
    final GetTenantMethod getTenantMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetTenantResponse> getTenant(@Valid final GetTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<GetTenantDashboardResponse> getTenantDashboard(@Valid final GetTenantDashboardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenantDashboard,
                getTenantDashboard::getTenantDashboard);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(@Valid final SyncTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(@Valid final DeleteTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(@Valid final ViewTenantPermissionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewTenantPermissions,
                viewTenantPermissionsMethod::viewTenantPermissions);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(@Valid final HasTenantPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasTenantPermission,
                hasTenantPermissionMethod::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid final SyncTenantPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantPermission,
                syncTenantPermissionMethod::syncTenantPermission);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(
            @Valid final DeleteTenantPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenantPermission,
                deleteTenantPermissionMethod::deleteTenantPermission);
    }
}
