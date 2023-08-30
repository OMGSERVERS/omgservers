package com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionModeModel;

import java.util.List;

public interface DoGreedyMatchmakingOperation {
    GreedyMatchmakingResult doGreedyMatchmaking(Long tenantId,
                                                Long stageId,
                                                Long versionId,
                                                Long matchmakerId,
                                                VersionModeModel modeConfig,
                                                List<RequestModel> activeRequests,
                                                List<MatchModel> launchedMatches);
}