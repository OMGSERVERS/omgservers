package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod;

import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request);
}
