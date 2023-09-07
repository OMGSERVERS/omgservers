package com.omgservers.module.tenant.impl.service.tenantService;

import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantService {

    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    Uni<Void> deleteTenant(DeleteTenantRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);
}
