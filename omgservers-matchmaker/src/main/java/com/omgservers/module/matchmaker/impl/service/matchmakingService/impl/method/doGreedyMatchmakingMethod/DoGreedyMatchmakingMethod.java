package com.omgservers.module.matchmaker.impl.service.matchmakingService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.dto.matchmaker.DoMatchmakingRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingResponse;
import io.smallrye.mutiny.Uni;

public interface DoGreedyMatchmakingMethod {
    Uni<DoMatchmakingResponse> doGreedyMatchmaking(DoMatchmakingRequest request);
}
