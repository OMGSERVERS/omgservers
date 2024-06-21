package com.omgservers.service.entrypoint.player.impl.service.webService;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateUserPlayerResponse> createUser(CreateUserPlayerRequest request);

    Uni<CreateTokenPlayerResponse> createToken(CreateTokenPlayerRequest request);

    Uni<CreateClientPlayerResponse> createClient(CreateClientPlayerRequest request);

    Uni<InterchangePlayerResponse> interchange(InterchangePlayerRequest request);
}
