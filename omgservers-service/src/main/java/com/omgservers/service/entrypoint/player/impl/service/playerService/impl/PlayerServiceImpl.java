package com.omgservers.service.entrypoint.player.impl.service.playerService.impl;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser.CreateUserMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createClient.CreateClientMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.interchangeMethod.InterchangeMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PlayerServiceImpl implements PlayerService {

    final CreateClientMethod createClientMethod;
    final InterchangeMethod interchangeMethod;
    final CreateTokenMethod createTokenMethod;
    final CreateUserMethod createUserMethod;

    @Override
    public Uni<CreateUserPlayerResponse> createUser(@Valid final CreateUserPlayerRequest request) {
        return createUserMethod.createUser(request);
    }

    @Override
    public Uni<CreateTokenPlayerResponse> createToken(@Valid final CreateTokenPlayerRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<CreateClientPlayerResponse> createClient(@Valid final CreateClientPlayerRequest request) {
        return createClientMethod.createClient(request);
    }

    @Override
    public Uni<InterchangePlayerResponse> interchange(@Valid final InterchangePlayerRequest request) {
        return interchangeMethod.interchange(request);
    }
}
