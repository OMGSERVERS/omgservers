package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionDataMethod {
    Uni<GetTenantVersionDataResponse> execute(ShardModel shardModel, GetTenantVersionDataRequest request);
}
