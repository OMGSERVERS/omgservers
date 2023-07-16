package com.omgservers.application.module.userModule.impl.service.playerHelpService.impl.method.getOrCreatePlayerHelpMethod;

import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetOrCreatePlayerHelpMethod {
    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}
