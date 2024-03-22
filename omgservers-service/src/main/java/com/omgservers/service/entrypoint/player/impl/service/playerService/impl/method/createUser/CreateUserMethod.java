package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser;

import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateUserMethod {
    Uni<CreateUserPlayerResponse> createUser(CreateUserPlayerRequest request);
}
