package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantProjectPermissionMethod {
    Uni<SyncTenantProjectPermissionResponse> execute(ShardModel shardModel, SyncTenantProjectPermissionRequest request);
}
