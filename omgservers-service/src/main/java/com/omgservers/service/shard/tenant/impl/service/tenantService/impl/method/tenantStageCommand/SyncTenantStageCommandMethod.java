package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantStageCommandMethod {
    Uni<SyncTenantStageCommandResponse> execute(ShardModel shardModel, SyncTenantStageCommandRequest request);
}