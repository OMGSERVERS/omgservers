package com.omgservers.module.user.impl.service.attributeService.impl.method.getAttribute;

import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetAttributeMethod {
    Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request);
}
