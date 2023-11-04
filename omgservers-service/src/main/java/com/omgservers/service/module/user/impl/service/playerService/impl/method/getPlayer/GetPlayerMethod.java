package com.omgservers.service.module.user.impl.service.playerService.impl.method.getPlayer;

import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);
}
