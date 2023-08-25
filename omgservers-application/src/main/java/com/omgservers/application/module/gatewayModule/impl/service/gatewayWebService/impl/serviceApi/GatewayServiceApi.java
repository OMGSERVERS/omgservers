package com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.impl.serviceApi;

import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/gateway-api/v1/request")
public interface GatewayServiceApi {

    @PUT
    @Path("/respond-message")
    Uni<Void> respondMessage(RespondMessageInternalRequest request);

    default void respondMessage(long timeout, RespondMessageInternalRequest request) {
        respondMessage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/assign-player")
    Uni<Void> assignPlayer(AssignPlayerInternalRequest request);

    default void assignPlayer(long timeout, AssignPlayerInternalRequest request) {
        assignPlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
