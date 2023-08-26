package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.syncTenantPermissionMethod;

import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request);
}
