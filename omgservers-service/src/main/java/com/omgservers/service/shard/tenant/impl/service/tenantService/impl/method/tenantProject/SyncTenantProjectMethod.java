package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantProjectMethod {
    Uni<SyncTenantProjectResponse> execute(ShardModel shardModel, SyncTenantProjectRequest request);
}
