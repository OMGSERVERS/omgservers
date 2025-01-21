package com.omgservers.service.entrypoint.player.impl.service.webService.impl.playerApi;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import com.omgservers.schema.entrypoint.player.PingServicePlayerRequest;
import com.omgservers.schema.entrypoint.player.PingServicePlayerResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Player Entrypoint API")
@Path("/service/v1/entrypoint/player/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.PLAYER_SECURITY_SCHEMA)
public interface PlayerApi {

    @POST
    @Path("/ping-service")
    Uni<PingServicePlayerResponse> execute(@NotNull PingServicePlayerRequest request);

    @POST
    @Path("/create-user")
    Uni<CreateUserPlayerResponse> execute(@NotNull CreateUserPlayerRequest request);

    @POST
    @Path("/create-token")
    Uni<CreateTokenPlayerResponse> execute(@NotNull CreateTokenPlayerRequest request);

    @POST
    @Path("/create-client")
    Uni<CreateClientPlayerResponse> execute(@NotNull CreateClientPlayerRequest request);

    @POST
    @Path("/interchange")
    Uni<InterchangePlayerResponse> execute(@NotNull InterchangePlayerRequest request);
}
