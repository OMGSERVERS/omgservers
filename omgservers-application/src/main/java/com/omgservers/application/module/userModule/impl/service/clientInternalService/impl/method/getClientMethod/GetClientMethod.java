package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.getClientMethod;

import com.omgservers.dto.userModule.GetClientInternalRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);
}
