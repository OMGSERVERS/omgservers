package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionMethod {
    Uni<DeleteTenantPermissionResponse> execute(ShardModel shardModel, DeleteTenantPermissionRequest request);
}
