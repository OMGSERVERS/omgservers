package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantMethod {
    Uni<GetTenantResponse> getTenant(ShardModel shardModel, GetTenantRequest request);
}
