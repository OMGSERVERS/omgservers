package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import io.smallrye.mutiny.Uni;

public interface VerifyTenantPermissionExistsMethod {
    Uni<VerifyTenantPermissionExistsResponse> execute(ShardModel shardModel, VerifyTenantPermissionExistsRequest request);
}
