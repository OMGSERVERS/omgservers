package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.versionModule.model.VersionModeModel;

import java.util.List;
import java.util.UUID;

public interface DoGreedyMatchmakingOperation {
    GreedyMatchmakingResult doGreedyMatchmaking(UUID tenant,
                                                UUID stage,
                                                UUID version,
                                                UUID matchmaker,
                                                VersionModeModel modeConfig,
                                                List<RequestModel> activeRequests,
                                                List<MatchModel> launchedMatches);
}
