package com.omgservers.module.tenant.impl.service.tenantShardedService;

import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantShardedService {

    Uni<GetTenantResponse> getTenant(GetTenantShardedRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantShardedRequest request);

    Uni<Void> deleteTenant(DeleteTenantShardedRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardedRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request);
}
