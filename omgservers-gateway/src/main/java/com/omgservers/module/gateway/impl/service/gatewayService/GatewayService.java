package com.omgservers.module.gateway.impl.service.gatewayService;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GatewayService {

    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);

    default RespondMessageResponse respondMessage(long timeout, RespondMessageRequest request) {
        return respondMessage(request).await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<Void> assignPlayer(AssignPlayerRequest request);

    default void assignPlayer(long timeout, AssignPlayerRequest request) {
        assignPlayer(request).await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<Void> assignRuntime(AssignRuntimeRequest request);

    default void assignRuntime(long timeout, AssignRuntimeRequest request) {
        assignRuntime(request).await().atMost(Duration.ofSeconds(timeout));
    }
}
