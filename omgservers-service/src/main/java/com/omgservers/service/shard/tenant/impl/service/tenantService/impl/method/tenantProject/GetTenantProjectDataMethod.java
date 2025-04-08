package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectDataMethod {
    Uni<GetTenantProjectDataResponse> execute(ShardModel shardModel, GetTenantProjectDataRequest request);
}
