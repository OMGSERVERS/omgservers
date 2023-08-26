package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.deleteTenantMethod.DeleteTenantMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod.GetTenantServiceMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.hasTenantPermissionMethod.HasTenantPermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantMethod.SyncTenantMethod;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod.SyncTenantPermissionMethod;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
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
    public Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetTenantRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getTenant,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenant,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteTenantRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteTenant,
                deleteTenantMethod::deleteTenant);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasTenantPermissionRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasTenantPermission,
                hasTenantPermissionMethod::hasTenantPermission);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncTenantPermissionRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncTenantPermission,
                syncTenantPermissionMethod::syncTenantPermission);
    }
}
