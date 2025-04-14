package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncTenantImageMethod {
    Uni<SyncTenantImageResponse> execute(ShardModel shardModel, SyncTenantImageRequest request);
}
