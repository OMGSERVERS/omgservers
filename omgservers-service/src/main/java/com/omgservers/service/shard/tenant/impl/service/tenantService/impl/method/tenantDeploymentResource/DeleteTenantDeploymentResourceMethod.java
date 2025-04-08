package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantDeploymentResourceMethod {
    Uni<DeleteTenantDeploymentResourceResponse> execute(ShardModel shardModel,
                                                        DeleteTenantDeploymentResourceRequest request);
}
