package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser;

import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface CreateUserMethod {
    Uni<CreateUserPlayerResponse> execute(CreateUserPlayerRequest request);
}
