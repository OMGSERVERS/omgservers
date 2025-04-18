package com.omgservers.service.entrypoint.player.impl.service.playerService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface PlayerService {

    Uni<PingServicePlayerResponse> execute(@Valid PingServicePlayerRequest request);

    Uni<CreateUserPlayerResponse> execute(@Valid CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> execute(@Valid CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> execute(@Valid CreateClientPlayerRequest request);

    Uni<InterchangeMessagesPlayerResponse> execute(@Valid InterchangeMessagesPlayerRequest request);
}
