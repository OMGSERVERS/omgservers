package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantProjectPermissionsMethod {
    Uni<ViewTenantProjectPermissionsResponse> execute(ShardModel shardModel,
                                                      ViewTenantProjectPermissionsRequest request);
}
