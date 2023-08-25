package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService;

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

public interface TenantInternalService {

    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);

    Uni<Void> deleteTenant(DeleteTenantInternalRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request);
}
