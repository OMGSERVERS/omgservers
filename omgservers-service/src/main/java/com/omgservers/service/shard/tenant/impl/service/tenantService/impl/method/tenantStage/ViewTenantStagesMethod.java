package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantStagesMethod {
    Uni<ViewTenantStagesResponse> execute(ShardModel shardModel, ViewTenantStagesRequest request);
}
