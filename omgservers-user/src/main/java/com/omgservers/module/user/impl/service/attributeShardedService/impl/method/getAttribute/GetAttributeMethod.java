package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.getAttribute;

import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetAttributeMethod {
    Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request);
}
