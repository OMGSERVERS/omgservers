package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDeploymentResourceMethod {
    Uni<GetTenantDeploymentResourceResponse> execute(ShardModel shardModel, GetTenantDeploymentResourceRequest request);
}
