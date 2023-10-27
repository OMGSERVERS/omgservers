package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import io.smallrye.mutiny.Uni;

public interface RespondMessageMethod {
    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);
}
