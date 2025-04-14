package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageDataMethod {
    Uni<GetTenantStageDataResponse> execute(ShardModel shardModel, GetTenantStageDataRequest request);
}
