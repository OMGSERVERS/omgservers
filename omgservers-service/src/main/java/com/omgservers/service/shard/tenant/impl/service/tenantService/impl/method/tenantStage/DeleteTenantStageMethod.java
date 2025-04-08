package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStageMethod {
    Uni<DeleteTenantStageResponse> execute(ShardModel shardModel, DeleteTenantStageRequest request);
}
