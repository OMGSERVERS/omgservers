package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayer;

import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);
}
