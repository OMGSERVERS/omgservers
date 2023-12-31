package com.omgservers.service.module.tenant.impl.service.tenantService;

import com.omgservers.model.dto.tenant.DeleteTenantPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteTenantPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewTenantPermissionsResponse;
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
