package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantImageMethod {
    Uni<GetTenantImageResponse> execute(ShardModel shardModel, GetTenantImageRequest request);
}
