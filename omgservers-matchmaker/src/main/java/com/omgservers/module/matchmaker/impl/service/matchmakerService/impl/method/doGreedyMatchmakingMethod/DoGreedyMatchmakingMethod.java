package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.dto.matchmaker.DoGreedyMatchmakingRequest;
import io.smallrye.mutiny.Uni;

public interface DoGreedyMatchmakingMethod {
    Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingRequest request);
}
