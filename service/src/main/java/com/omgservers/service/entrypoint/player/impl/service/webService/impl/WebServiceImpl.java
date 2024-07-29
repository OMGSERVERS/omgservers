package com.omgservers.service.entrypoint.player.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;
import com.omgservers.service.entrypoint.player.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final PlayerService playerService;

    @Override
    public Uni<CreateUserPlayerResponse> createUser(final CreateUserPlayerRequest request) {
        return playerService.createUser(request);
    }

    @Override
    public Uni<CreateTokenPlayerResponse> createToken(final CreateTokenPlayerRequest request) {
        return playerService.createToken(request);
    }

    @Override
    public Uni<CreateClientPlayerResponse> createClient(final CreateClientPlayerRequest request) {
        return playerService.createClient(request);
    }

    @Override
    public Uni<InterchangePlayerResponse> interchange(final InterchangePlayerRequest request) {
        return playerService.interchange(request);
    }
}
