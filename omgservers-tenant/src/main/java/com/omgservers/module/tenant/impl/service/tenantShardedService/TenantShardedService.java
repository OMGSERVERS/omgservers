package com.omgservers.module.tenant.impl.service.tenantShardedService;

import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantShardedService {

    Uni<GetTenantResponse> getTenant(GetTenantShardRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request);

    Uni<Void> deleteTenant(DeleteTenantShardRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request);
}
