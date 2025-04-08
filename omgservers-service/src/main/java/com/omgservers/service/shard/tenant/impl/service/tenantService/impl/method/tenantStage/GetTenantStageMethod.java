package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageMethod {
    Uni<GetTenantStageResponse> execute(ShardModel shardModel, GetTenantStageRequest request);
}
