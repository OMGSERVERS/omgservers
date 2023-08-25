package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.respondMessageMethod;

import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;

public interface RespondMessageMethod {
    Uni<Void> respondMessage(RespondMessageInternalRequest request);
}
