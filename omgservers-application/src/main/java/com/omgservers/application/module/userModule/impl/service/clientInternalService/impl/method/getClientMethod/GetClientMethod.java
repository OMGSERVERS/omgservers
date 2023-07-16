package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.getClientMethod;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request);
}
