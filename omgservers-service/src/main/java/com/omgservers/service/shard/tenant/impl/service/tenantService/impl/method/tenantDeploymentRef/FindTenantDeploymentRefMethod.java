package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantDeploymentRefMethod {
    Uni<FindTenantDeploymentRefResponse> execute(ShardModel shardModel, FindTenantDeploymentRefRequest request);
}
