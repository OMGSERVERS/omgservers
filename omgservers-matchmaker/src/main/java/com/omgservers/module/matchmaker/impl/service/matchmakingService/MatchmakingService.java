package com.omgservers.module.matchmaker.impl.service.matchmakingService;

import com.omgservers.dto.matchmaker.DoMatchmakingRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingResponse;
import io.smallrye.mutiny.Uni;

public interface MatchmakingService {

    Uni<DoMatchmakingResponse> doGreedyMatchmaking(DoMatchmakingRequest request);
}
