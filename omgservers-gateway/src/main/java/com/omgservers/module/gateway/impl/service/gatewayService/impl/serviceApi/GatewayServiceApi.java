package com.omgservers.module.gateway.impl.service.gatewayService.impl.serviceApi;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/gateway-api/v1/request")
public interface GatewayServiceApi {

    @PUT
    @Path("/respond-message")
    Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request);

    default RespondMessageResponse respondMessage(long timeout, RespondMessageRequest request) {
        return respondMessage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/assign-player")
    Uni<Void> assignPlayer(AssignPlayerRequest request);

    default void assignPlayer(long timeout, AssignPlayerRequest request) {
        assignPlayer(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/assign-runtime")
    Uni<Void> assignRuntime(AssignRuntimeRequest request);

    default void assignRuntime(long timeout, AssignRuntimeRequest request) {
        assignRuntime(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
