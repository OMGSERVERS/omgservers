package com.omgservers.service.entrypoint.player.impl.service.playerService;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PlayerService {

    Uni<CreateUserPlayerResponse> createUser(@Valid CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> createToken(@Valid CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> createClient(@Valid CreateClientPlayerRequest request);

    Uni<InterchangePlayerResponse> interchange(@Valid InterchangePlayerRequest request);
}
