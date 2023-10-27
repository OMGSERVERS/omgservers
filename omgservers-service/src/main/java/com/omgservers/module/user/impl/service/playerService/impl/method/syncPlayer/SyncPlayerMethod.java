package com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer;

import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);
}
