package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantVersionMethod {
    Uni<DeleteTenantVersionResponse> execute(ShardModel shardModel, DeleteTenantVersionRequest request);
}
