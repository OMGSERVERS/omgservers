package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.deleteAttribute;

import com.omgservers.dto.user.DeleteAttributeShardResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteAttributeMethod {
    Uni<DeleteAttributeShardResponse> deleteAttribute(DeleteAttributeShardedRequest request);
}
