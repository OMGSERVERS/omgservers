package com.omgservers.module.tenant.impl.service.tenantShardedService.impl;

import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.GetTenantServiceApiClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.TenantServiceApiClient;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.getTenant.GetTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.hasTenantPermission.HasTenantPermissionMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant.SyncTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission.SyncTenantPermissionMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantShardedServiceImpl implements TenantShardedService {

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final HasTenantPermissionMethod hasTenantPermissionMethod;
    final GetTenantMethod getTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;

    @Override
    public Uni<GetTenantResponse> getTenant(GetTenantShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetTenantShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteTenantShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasTenantPermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasTenantPermission,
                hasTenantPermissionMethod::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantPermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenantPermission,
                syncTenantPermissionMethod::syncTenantPermission);
    }
}
