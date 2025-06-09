package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageCommandMethod {
    Uni<DeleteTenantStageCommandResponse> execute(ShardModel shardModel, DeleteTenantStageCommandRequest request);
}