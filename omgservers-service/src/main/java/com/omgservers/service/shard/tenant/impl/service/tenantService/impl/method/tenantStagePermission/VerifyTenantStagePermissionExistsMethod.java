package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import io.smallrye.mutiny.Uni;

public interface VerifyTenantStagePermissionExistsMethod {
    Uni<VerifyTenantStagePermissionExistsResponse> execute(ShardModel shardModel,
                                                           VerifyTenantStagePermissionExistsRequest request);
}
