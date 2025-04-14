package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantStagePermissionsMethod {
    Uni<ViewTenantStagePermissionsResponse> execute(ShardModel shardModel, ViewTenantStagePermissionsRequest request);
}
