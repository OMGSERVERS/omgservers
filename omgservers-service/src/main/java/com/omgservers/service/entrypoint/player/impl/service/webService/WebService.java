package com.omgservers.service.entrypoint.player.impl.service.webService;

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

public interface WebService {

    Uni<PingServicePlayerResponse> execute(PingServicePlayerRequest request);

    Uni<CreateUserPlayerResponse> execute(CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> execute(CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> execute(CreateClientPlayerRequest request);

    Uni<InterchangeMessagesPlayerResponse> execute(InterchangeMessagesPlayerRequest request);
}
