package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.deleteAttribute;

import com.omgservers.dto.user.DeleteAttributeShardedResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteAttributeMethod {
    Uni<DeleteAttributeShardedResponse> deleteAttribute(DeleteAttributeShardedRequest request);
}
