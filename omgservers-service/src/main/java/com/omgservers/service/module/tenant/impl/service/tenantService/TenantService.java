package com.omgservers.service.module.tenant.impl.service.tenantService;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface TenantService {

    Uni<GetTenantResponse> getTenant(@Valid GetTenantRequest request);

    Uni<SyncTenantResponse> syncTenant(@Valid SyncTenantRequest request);

    Uni<DeleteTenantResponse> deleteTenant(@Valid DeleteTenantRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(@Valid HasTenantPermissionRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid SyncTenantPermissionRequest request);
}
