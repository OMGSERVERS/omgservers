package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantDeploymentResourcesMethod {
    Uni<ViewTenantDeploymentResourcesResponse> execute(ShardModel shardModel,
                                                       ViewTenantDeploymentResourcesRequest request);
}
