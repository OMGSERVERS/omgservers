package com.omgservers.module.matchmaker.impl.service.matchmakerService;

import com.omgservers.dto.matchmaker.DoGreedyMatchmakingRequest;
import io.smallrye.mutiny.Uni;

// TODO: remove this service -> operation
public interface MatchmakerService {

    Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingRequest request);
}
