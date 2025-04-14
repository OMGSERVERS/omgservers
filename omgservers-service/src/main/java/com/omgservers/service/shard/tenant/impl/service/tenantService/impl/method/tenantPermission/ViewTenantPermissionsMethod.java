package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantPermissionsMethod {
    Uni<ViewTenantPermissionsResponse> execute(ShardModel shardModel, ViewTenantPermissionsRequest request);
}
