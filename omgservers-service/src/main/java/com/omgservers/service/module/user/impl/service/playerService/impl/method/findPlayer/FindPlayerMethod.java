package com.omgservers.service.module.user.impl.service.playerService.impl.method.findPlayer;

import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPlayerMethod {
    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);
}
