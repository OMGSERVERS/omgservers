package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl;

import com.omgservers.dto.matchmaker.DoGreedyMatchmakingRequest;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.doGreedyMatchmakingMethod.DoGreedyMatchmakingMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerServiceImpl implements MatchmakerService {

    final DoGreedyMatchmakingMethod doGreedyMatchmakingMethod;

    @Override
    public Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingRequest request) {
        return doGreedyMatchmakingMethod.doGreedyMatchmaking(request);
    }
}
