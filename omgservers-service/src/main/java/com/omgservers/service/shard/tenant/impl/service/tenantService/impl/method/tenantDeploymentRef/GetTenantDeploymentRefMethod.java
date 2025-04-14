package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentRefMethod {
    Uni<GetTenantDeploymentRefResponse> execute(ShardModel shardModel, GetTenantDeploymentRefRequest request);
}
