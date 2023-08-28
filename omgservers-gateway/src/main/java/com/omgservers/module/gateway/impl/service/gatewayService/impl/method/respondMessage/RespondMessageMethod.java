package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface RespondMessageMethod {
    Uni<Void> respondMessage(RespondMessageRoutedRequest request);
}
