package com.omgservers.service.module.player.impl.service.playerService.impl.method.createClient;

import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateClientMethod {
    Uni<CreateClientPlayerResponse> createClient(CreateClientPlayerRequest request);
}
