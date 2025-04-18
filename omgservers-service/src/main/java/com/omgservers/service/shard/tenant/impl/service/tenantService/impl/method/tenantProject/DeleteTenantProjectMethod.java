package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantProjectMethod {
    Uni<DeleteTenantProjectResponse> execute(ShardModel shardModel, DeleteTenantProjectRequest request);
}
