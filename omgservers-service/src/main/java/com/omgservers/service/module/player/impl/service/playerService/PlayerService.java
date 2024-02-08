package com.omgservers.service.module.player.impl.service.playerService;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PlayerService {

    Uni<CreateUserPlayerResponse> createUser(@Valid CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> createToken(@Valid CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> createClient(@Valid CreateClientPlayerRequest request);

    Uni<InterchangePlayerResponse> interchange(@Valid InterchangePlayerRequest request);
}
