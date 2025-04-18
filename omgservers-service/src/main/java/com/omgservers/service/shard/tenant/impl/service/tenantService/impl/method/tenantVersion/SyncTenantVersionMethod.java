package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantVersionMethod {
    Uni<SyncTenantVersionResponse> execute(ShardModel shardModel, SyncTenantVersionRequest request);
}
