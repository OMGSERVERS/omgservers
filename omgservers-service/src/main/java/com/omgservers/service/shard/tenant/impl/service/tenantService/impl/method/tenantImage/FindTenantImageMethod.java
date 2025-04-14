package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageResponse;
import io.smallrye.mutiny.Uni;

public interface FindTenantImageMethod {
    Uni<FindTenantImageResponse> execute(ShardModel shardModel, FindTenantImageRequest request);
}
