package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetAttributeMethod {
    Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request);
}
