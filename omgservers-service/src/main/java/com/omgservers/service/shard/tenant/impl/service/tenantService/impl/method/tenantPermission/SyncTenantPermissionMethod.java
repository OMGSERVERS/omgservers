package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantPermissionMethod {
    Uni<SyncTenantPermissionResponse> execute(ShardModel shardModel, SyncTenantPermissionRequest request);
}
