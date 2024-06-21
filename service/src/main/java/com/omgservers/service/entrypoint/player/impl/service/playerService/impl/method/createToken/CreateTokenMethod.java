package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createToken;

import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTokenMethod {
    Uni<CreateTokenPlayerResponse> createToken(CreateTokenPlayerRequest request);
}
