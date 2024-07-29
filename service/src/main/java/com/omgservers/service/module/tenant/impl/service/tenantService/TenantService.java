package com.omgservers.service.module.tenant.impl.service.tenantService;

import com.omgservers.schema.module.tenant.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.GetTenantDashboardRequest;
import com.omgservers.schema.module.tenant.GetTenantDashboardResponse;
import com.omgservers.schema.module.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import com.omgservers.schema.module.tenant.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.SyncTenantPermissionResponse;
import com.omgservers.schema.module.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.SyncTenantResponse;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewTenantPermissionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TenantService {

    Uni<GetTenantResponse> getTenant(@Valid GetTenantRequest request);

    Uni<GetTenantDashboardResponse> getTenantDashboard(@Valid GetTenantDashboardRequest request);

    Uni<SyncTenantResponse> syncTenant(@Valid SyncTenantRequest request);

    Uni<DeleteTenantResponse> deleteTenant(@Valid DeleteTenantRequest request);

    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(@Valid ViewTenantPermissionsRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(@Valid HasTenantPermissionRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(@Valid DeleteTenantPermissionRequest request);
}
