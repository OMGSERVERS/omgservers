package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import io.smallrye.mutiny.Uni;

public interface RespondMessageMethod {
    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);
}
