package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantProjectsMethod {
    Uni<ViewTenantProjectsResponse> execute(ShardModel shardModel, ViewTenantProjectsRequest request);
}
