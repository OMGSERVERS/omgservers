package com.omgservers.service.module.player.impl.service.webService.impl.playerApi;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Player API")
@Path("/omgservers/player-api/v1/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.PLAYER_SECURITY_SCHEMA)
public interface PlayerApi {

    @PUT
    @Path("/create-user")
    Uni<CreateUserPlayerResponse> createUser(CreateUserPlayerRequest request);

    @PUT
    @Path("/create-token")
    Uni<CreateTokenPlayerResponse> createToken(CreateTokenPlayerRequest request);

    @PUT
    @Path("/create-client")
    Uni<CreateClientPlayerResponse> createClient(CreateClientPlayerRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangePlayerResponse> interchange(InterchangePlayerRequest request);
}
