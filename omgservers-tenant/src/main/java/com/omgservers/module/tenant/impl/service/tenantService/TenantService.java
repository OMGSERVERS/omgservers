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
import jakarta.validation.Valid;

public interface TenantService {

    Uni<GetTenantResponse> getTenant(@Valid GetTenantRequest request);

    Uni<SyncTenantResponse> syncTenant(@Valid SyncTenantRequest request);

    Uni<Void> deleteTenant(@Valid DeleteTenantRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(@Valid HasTenantPermissionRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid SyncTenantPermissionRequest request);
}
