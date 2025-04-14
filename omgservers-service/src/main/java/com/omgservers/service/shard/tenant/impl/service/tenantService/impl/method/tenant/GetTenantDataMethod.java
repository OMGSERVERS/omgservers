package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDataMethod {
    Uni<GetTenantDataResponse> getTenantData(ShardModel shardModel, GetTenantDataRequest request);
}
