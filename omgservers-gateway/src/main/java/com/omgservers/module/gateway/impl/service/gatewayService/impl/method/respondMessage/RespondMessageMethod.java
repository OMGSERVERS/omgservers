package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.omgservers.dto.gateway.RespondMessageRequest;
import io.smallrye.mutiny.Uni;

public interface RespondMessageMethod {
    Uni<Void> respondMessage(RespondMessageRequest request);
}
