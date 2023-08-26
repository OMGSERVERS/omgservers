package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod;

import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetAttributeMethod {
    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request);
}
