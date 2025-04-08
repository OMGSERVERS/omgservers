package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import io.smallrye.mutiny.Uni;

public interface VerifyTenantProjectPermissionExistsMethod {
    Uni<VerifyTenantProjectPermissionExistsResponse> execute(ShardModel shardModel,
                                                             VerifyTenantProjectPermissionExistsRequest request);
}
