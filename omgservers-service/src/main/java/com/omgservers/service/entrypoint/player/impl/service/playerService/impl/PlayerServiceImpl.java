package com.omgservers.service.entrypoint.player.impl.service.playerService.impl;

import com.omgservers.schema.entrypoint.player.*;
import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.*;
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

    final InterchangeMessagesMethod interchangeMessagesMethod;
    final CreateClientMethod createClientMethod;
    final CreateTokenMethod createTokenMethod;
    final PingServiceMethod pingServiceMethod;
    final CreateUserMethod createUserMethod;

    @Override
    public Uni<PingServicePlayerResponse> execute(@Valid final PingServicePlayerRequest request) {
        return pingServiceMethod.execute(request);
    }

    @Override
    public Uni<CreateUserPlayerResponse> execute(@Valid final CreateUserPlayerRequest request) {
        return createUserMethod.execute(request);
    }

    @Override
    public Uni<CreateTokenPlayerResponse> execute(@Valid final CreateTokenPlayerRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<CreateClientPlayerResponse> execute(@Valid final CreateClientPlayerRequest request) {
        return createClientMethod.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesPlayerResponse> execute(@Valid final InterchangeMessagesPlayerRequest request) {
        return interchangeMessagesMethod.execute(request);
    }
}
