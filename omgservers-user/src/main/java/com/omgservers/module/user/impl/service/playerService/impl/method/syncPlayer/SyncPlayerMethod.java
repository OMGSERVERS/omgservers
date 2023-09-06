package com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer;

import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request);
}
