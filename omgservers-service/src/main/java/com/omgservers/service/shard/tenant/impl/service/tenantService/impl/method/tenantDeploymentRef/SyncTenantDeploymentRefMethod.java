package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.SyncTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.SyncTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantDeploymentRefMethod {
    Uni<SyncTenantDeploymentRefResponse> execute(ShardModel shardModel, SyncTenantDeploymentRefRequest request);
}
