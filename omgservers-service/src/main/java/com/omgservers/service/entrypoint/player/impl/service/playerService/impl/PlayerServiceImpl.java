package com.omgservers.service.entrypoint.player.impl.service.playerService.impl;

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
import com.omgservers.service.entrypoint.player.impl.service.playerService.PlayerService;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.PingServiceMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createClient.CreateClientMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser.CreateUserMethod;
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
    public Uni<InterchangePlayerResponse> execute(@Valid final InterchangePlayerRequest request) {
        return interchangeMethod.execute(request);
    }
}
