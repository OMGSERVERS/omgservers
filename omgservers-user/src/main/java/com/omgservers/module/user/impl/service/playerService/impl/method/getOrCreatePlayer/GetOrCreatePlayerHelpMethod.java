package com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer;

import com.omgservers.dto.user.GetOrCreatePlayerHelpRequest;
import com.omgservers.dto.user.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetOrCreatePlayerHelpMethod {
    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}
