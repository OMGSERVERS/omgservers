package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantStageCommandsMethod {
    Uni<ViewTenantStageCommandResponse> execute(ShardModel shardModel, ViewTenantStageCommandRequest request);
}