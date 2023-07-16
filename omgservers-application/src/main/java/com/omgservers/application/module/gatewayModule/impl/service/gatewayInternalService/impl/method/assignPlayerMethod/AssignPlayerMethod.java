package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.assignPlayerMethod;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import io.smallrye.mutiny.Uni;

public interface AssignPlayerMethod {
    Uni<Void> assignPlayer(AssignPlayerInternalRequest request);
}
