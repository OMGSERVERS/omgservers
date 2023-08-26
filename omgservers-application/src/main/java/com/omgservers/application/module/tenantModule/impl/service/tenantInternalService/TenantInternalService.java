package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService;

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

public interface TenantInternalService {

    Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantRoutedRequest request);

    Uni<Void> deleteTenant(DeleteTenantRoutedRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request);
}
