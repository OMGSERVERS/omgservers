package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakingResults;

import com.omgservers.dto.matchmaker.SyncMatchmakingResultsRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsResponse;
import io.smallrye.mutiny.Uni;

public interface SyncMatchmakingResultsMethod {

    Uni<SyncMatchmakingResultsResponse> syncMatchmakingResults(SyncMatchmakingResultsRequest request);
}
