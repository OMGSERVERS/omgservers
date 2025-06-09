package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageStateMethod {
    Uni<GetTenantStageStateResponse> execute(ShardModel shardModel, GetTenantStageStateRequest request);
}