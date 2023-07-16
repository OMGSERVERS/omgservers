package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.createClientMethod;

import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import io.smallrye.mutiny.Uni;

public interface CreateClientMethod {

    Uni<CreateClientInternalResponse> createClient(CreateClientInternalRequest request);
}
