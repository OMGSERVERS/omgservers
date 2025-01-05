package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateClientMethod {
    Uni<CreateClientPlayerResponse> execute(CreateClientPlayerRequest request);
}
