package com.omgservers.module.user.impl.service.playerShardedService.impl.method.getPlayer;

import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request);
}
