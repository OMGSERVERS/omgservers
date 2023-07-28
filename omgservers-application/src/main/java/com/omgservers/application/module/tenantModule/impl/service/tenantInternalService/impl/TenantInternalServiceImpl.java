package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.hasTenantPermissionMethod.HasTenantPermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod.SyncTenantPermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.deleteTenantMethod.DeleteTenantMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod.GetTenantServiceMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod.SyncTenantMethod;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.*;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.GetTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

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
