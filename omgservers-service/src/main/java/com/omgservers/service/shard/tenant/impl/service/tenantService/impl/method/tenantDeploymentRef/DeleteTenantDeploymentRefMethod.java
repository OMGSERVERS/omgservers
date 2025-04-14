package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentRefMethod {
    Uni<DeleteTenantDeploymentRefResponse> execute(ShardModel shardModel, DeleteTenantDeploymentRefRequest request);
}
