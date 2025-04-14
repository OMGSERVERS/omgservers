package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewTenantImagesMethod {
    Uni<ViewTenantImagesResponse> execute(ShardModel shardModel, ViewTenantImagesRequest request);
}
