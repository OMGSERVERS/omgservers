package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectPermissionMethod {
    Uni<DeleteTenantProjectPermissionResponse> execute(ShardModel shardModel, DeleteTenantProjectPermissionRequest request);
}
