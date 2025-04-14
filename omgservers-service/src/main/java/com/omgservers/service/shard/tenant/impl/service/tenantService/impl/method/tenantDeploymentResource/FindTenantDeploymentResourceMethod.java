package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantDeploymentResourceMethod {
    Uni<FindTenantDeploymentResourceResponse> execute(ShardModel shardModel,
                                                      FindTenantDeploymentResourceRequest request);
}
