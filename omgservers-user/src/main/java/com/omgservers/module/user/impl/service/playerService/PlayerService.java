package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.dto.user.GetOrCreatePlayerHelpRequest;
import com.omgservers.dto.user.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerService {
    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}
