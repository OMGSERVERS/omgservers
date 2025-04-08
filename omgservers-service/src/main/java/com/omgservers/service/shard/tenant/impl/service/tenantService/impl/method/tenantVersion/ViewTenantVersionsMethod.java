package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantVersionsMethod {
    Uni<ViewTenantVersionsResponse> execute(ShardModel shardModel, ViewTenantVersionsRequest request);
}
