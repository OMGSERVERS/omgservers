package com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer;

import com.omgservers.module.user.impl.service.playerService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.module.user.impl.service.playerService.response.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetOrCreatePlayerHelpMethod {
    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}
