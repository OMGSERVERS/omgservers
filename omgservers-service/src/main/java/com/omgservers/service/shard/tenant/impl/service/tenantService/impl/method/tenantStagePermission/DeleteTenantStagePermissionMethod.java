package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStagePermissionMethod {
    Uni<DeleteTenantStagePermissionResponse> execute(ShardModel shardModel, DeleteTenantStagePermissionRequest request);
}
