package com.omgservers.module.gateway.impl.service.gatewayService;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface GatewayService {

    Uni<Void> respondMessage(RespondMessageRoutedRequest request);

    default void respondMessage(long timeout, RespondMessageRoutedRequest request) {
        respondMessage(request).await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<Void> assignPlayer(AssignPlayerRoutedRequest request);

    default void assignPlayer(long timeout, AssignPlayerRoutedRequest request) {
        assignPlayer(request).await().atMost(Duration.ofSeconds(timeout));
    }
}
