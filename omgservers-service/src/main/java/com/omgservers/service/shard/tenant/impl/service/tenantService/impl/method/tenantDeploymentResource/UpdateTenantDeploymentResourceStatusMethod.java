package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateTenantDeploymentResourceStatusMethod {
    Uni<UpdateTenantDeploymentResourceStatusResponse> execute(ShardModel shardModel,
                                                              UpdateTenantDeploymentResourceStatusRequest request);
}
