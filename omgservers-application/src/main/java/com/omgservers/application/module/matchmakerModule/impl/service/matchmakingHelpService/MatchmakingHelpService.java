package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import io.smallrye.mutiny.Uni;

public interface MatchmakingHelpService {

    Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingHelpRequest request);
}
