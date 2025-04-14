package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.DeleteTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantImageMethod {
    Uni<DeleteTenantImageResponse> execute(ShardModel shardModel, DeleteTenantImageRequest request);
}
