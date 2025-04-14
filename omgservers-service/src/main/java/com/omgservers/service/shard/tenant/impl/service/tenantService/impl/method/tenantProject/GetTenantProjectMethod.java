package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectMethod {
    Uni<GetTenantProjectResponse> execute(ShardModel shardModel, GetTenantProjectRequest request);
}
