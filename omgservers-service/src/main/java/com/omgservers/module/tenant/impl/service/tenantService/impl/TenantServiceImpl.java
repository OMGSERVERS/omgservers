package com.omgservers.module.tenant.impl.service.tenantService.impl;

import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.module.tenant.impl.service.tenantService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantService.impl.method.getTenant.GetTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantService.impl.method.hasTenantPermission.HasTenantPermissionMethod;
import com.omgservers.module.tenant.impl.service.tenantService.impl.method.syncTenant.SyncTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantService.impl.method.syncTenantPermission.SyncTenantPermissionMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
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

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final HasTenantPermissionMethod hasTenantPermissionMethod;
    final GetTenantMethod getTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;

    @Override
    public Uni<GetTenantResponse> getTenant(@Valid final GetTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(@Valid final SyncTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<Void> deleteTenant(@Valid final DeleteTenantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
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
}
