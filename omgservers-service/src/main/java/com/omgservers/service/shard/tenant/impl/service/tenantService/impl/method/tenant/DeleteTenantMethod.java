package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantResponse> deleteTenant(ShardModel shardModel, DeleteTenantRequest request);
}
