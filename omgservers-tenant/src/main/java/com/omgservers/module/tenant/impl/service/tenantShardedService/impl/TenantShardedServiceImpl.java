package com.omgservers.module.tenant.impl.service.tenantShardedService.impl;

import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.getTenant.GetTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.hasTenantPermission.HasTenantPermissionMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenant.SyncTenantMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.syncTenantPermission.SyncTenantPermissionMethod;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantShardedServiceImpl implements TenantShardedService {

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final HasTenantPermissionMethod hasTenantPermissionMethod;
    final GetTenantMethod getTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;

    @Override
    public Uni<GetTenantShardedResponse> getTenant(GetTenantShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetTenantShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteTenantShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    @Override
    public Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasTenantPermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasTenantPermission,
                hasTenantPermissionMethod::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantPermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncTenantPermission,
                syncTenantPermissionMethod::syncTenantPermission);
    }
}
