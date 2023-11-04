package com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer;

import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface SyncPlayerMethod {
    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);
}
