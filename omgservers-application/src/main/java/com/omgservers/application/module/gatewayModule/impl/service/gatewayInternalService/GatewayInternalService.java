package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GatewayInternalService {

    Uni<Void> respondMessage(RespondMessageInternalRequest request);

    default void respondMessage(long timeout, RespondMessageInternalRequest request) {
        respondMessage(request).await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<Void> assignPlayer(AssignPlayerInternalRequest request);

    default void assignPlayer(long timeout, AssignPlayerInternalRequest request) {
        assignPlayer(request).await().atMost(Duration.ofSeconds(timeout));
    }
}
