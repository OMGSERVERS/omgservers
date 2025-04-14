package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantStageMethod {
    Uni<SyncTenantStageResponse> execute(ShardModel shardModel, SyncTenantStageRequest request);
}
