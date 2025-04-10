package com.omgservers.service.entrypoint.player.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerResponse;
import com.omgservers.schema.entrypoint.player.PingServicePlayerRequest;
import com.omgservers.schema.entrypoint.player.PingServicePlayerResponse;
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
    public Uni<PingServicePlayerResponse> execute(final PingServicePlayerRequest request) {
        return playerService.execute(request);
    }

    @Override
    public Uni<CreateUserPlayerResponse> execute(final CreateUserPlayerRequest request) {
        return playerService.execute(request);
    }

    @Override
    public Uni<CreateTokenPlayerResponse> execute(final CreateTokenPlayerRequest request) {
        return playerService.execute(request);
    }

    @Override
    public Uni<CreateClientPlayerResponse> execute(final CreateClientPlayerRequest request) {
        return playerService.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesPlayerResponse> execute(final InterchangeMessagesPlayerRequest request) {
        return playerService.execute(request);
    }
}
