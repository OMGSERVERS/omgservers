package com.omgservers.service.entrypoint.player.impl.service.webService;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateUserPlayerResponse> createUser(CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> createToken(CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> createClient(CreateClientPlayerRequest request);

    Uni<InterchangePlayerResponse> interchange(InterchangePlayerRequest request);
}
