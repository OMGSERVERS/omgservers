package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionConfigMethod {
    Uni<GetTenantVersionConfigResponse> execute(ShardModel shardModel, GetTenantVersionConfigRequest request);
}
