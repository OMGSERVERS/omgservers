package com.omgservers.module.user.impl.service.playerShardedService.impl.method.deletePlayer;

import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request);
}
