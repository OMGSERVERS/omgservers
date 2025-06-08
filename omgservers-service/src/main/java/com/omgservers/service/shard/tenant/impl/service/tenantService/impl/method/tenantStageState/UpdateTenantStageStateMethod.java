package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateTenantStageStateMethod {
    Uni<UpdateTenantStageStateResponse> execute(ShardModel shardModel, UpdateTenantStageStateRequest request);
}