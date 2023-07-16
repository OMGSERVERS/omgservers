package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request);
}
