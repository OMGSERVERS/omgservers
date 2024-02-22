package com.omgservers.service.handler.job.task.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionModeModel;

import java.util.List;

public interface DoGreedyMatchmakingOperation {
    DoGreedyMatchmakingResult doGreedyMatchmaking(VersionModeModel modeConfig,
                                                  List<RequestModel> requests,
                                                  List<MatchModel> matches,
                                                  List<MatchClientModel> clients);
}
