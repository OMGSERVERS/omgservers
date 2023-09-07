package com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer;

import com.omgservers.dto.user.GetOrCreatePlayerRequest;
import com.omgservers.dto.user.GetOrCreatePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface GetOrCreatePlayerHelpMethod {
    Uni<GetOrCreatePlayerResponse> getOrCreatePlayer(GetOrCreatePlayerRequest request);
}
