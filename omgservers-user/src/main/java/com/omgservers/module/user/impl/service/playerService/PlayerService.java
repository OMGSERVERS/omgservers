package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.module.user.impl.service.playerService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.module.user.impl.service.playerService.response.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerService {
    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}
