package com.omgservers.module.matchmaker.impl.service.matchmakingService.impl;

import com.omgservers.dto.matchmaker.DoMatchmakingRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingResponse;
import com.omgservers.module.matchmaker.impl.service.matchmakingService.MatchmakingService;
import com.omgservers.module.matchmaker.impl.service.matchmakingService.impl.method.doGreedyMatchmakingMethod.DoGreedyMatchmakingMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakingServiceImpl implements MatchmakingService {

    final DoGreedyMatchmakingMethod doGreedyMatchmakingMethod;

    @Override
    public Uni<DoMatchmakingResponse> doGreedyMatchmaking(DoMatchmakingRequest request) {
        return doGreedyMatchmakingMethod.doGreedyMatchmaking(request);
    }
}
