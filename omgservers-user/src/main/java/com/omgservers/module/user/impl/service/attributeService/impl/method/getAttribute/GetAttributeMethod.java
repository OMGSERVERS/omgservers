package com.omgservers.module.user.impl.service.attributeService.impl.method.getAttribute;

import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.dto.user.GetAttributeRequest;
import io.smallrye.mutiny.Uni;

public interface GetAttributeMethod {
    Uni<GetAttributeResponse> getAttribute(GetAttributeRequest request);
}
