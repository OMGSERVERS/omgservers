package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionMethod {
    Uni<GetTenantVersionResponse> execute(ShardModel shardModel, GetTenantVersionRequest request);
}
