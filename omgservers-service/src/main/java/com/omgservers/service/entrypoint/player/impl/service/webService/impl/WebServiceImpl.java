package com.omgservers.service.entrypoint.player.impl.service.webService.impl;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
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
