package com.omgservers.module.gateway.impl.service.gatewayWebService.impl.serviceApi;

import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/gateway-api/v1/request")
public interface GatewayServiceApi {

    @PUT
    @Path("/respond-message")
    Uni<Void> respondMessage(RespondMessageRoutedRequest request);

    default void respondMessage(long timeout, RespondMessageRoutedRequest request) {
        respondMessage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/assign-player")
    Uni<Void> assignPlayer(AssignPlayerRoutedRequest request);

    default void assignPlayer(long timeout, AssignPlayerRoutedRequest request) {
        assignPlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
