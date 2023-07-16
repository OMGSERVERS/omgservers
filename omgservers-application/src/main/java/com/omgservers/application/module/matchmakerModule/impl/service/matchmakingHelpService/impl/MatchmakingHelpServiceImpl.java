package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.impl;

import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.MatchmakingHelpService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.impl.method.doGreedyMatchmakingMethod.DoGreedyMatchmakingMethod;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakingHelpServiceImpl implements MatchmakingHelpService {

    final DoGreedyMatchmakingMethod doGreedyMatchmakingMethod;

    @Override
    public Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingHelpRequest request) {
        return doGreedyMatchmakingMethod.doGreedyMatchmaking(request);
    }
}
