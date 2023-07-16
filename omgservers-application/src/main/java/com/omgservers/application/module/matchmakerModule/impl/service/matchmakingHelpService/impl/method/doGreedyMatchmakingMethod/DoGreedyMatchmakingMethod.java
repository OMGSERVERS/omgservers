package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import io.smallrye.mutiny.Uni;

public interface DoGreedyMatchmakingMethod {
    Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingHelpRequest request);
}
