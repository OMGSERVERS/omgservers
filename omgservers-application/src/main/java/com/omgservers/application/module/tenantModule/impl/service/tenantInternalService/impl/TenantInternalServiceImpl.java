package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.deleteTenantMethod.DeleteTenantMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod.GetTenantServiceMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.hasTenantPermissionMethod.HasTenantPermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod.SyncTenantMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod.SyncTenantPermissionMethod;
import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.DeleteTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
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
public class TenantInternalServiceImpl implements TenantInternalService {

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final HasTenantPermissionMethod hasTenantPermissionMethod;
    final GetTenantServiceMethod getTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;

    @Override
    public Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetTenantInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteTenantInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasTenantPermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasTenantPermission,
                hasTenantPermissionMethod::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantPermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenantPermission,
                syncTenantPermissionMethod::syncTenantPermission);
    }
}
