package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantDeploymentRefsMethod {
    Uni<ViewTenantDeploymentRefsResponse> execute(ShardModel shardModel, ViewTenantDeploymentRefsRequest request);
}
