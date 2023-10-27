package com.omgservers.module.user.impl.service.playerService.impl.method.findPlayer;

import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPlayerMethod {
    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);
}
