package com.omgservers.service.handler.job.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.model.version.VersionModeModel;

import java.util.List;

public interface DoGreedyMatchmakingOperation {
    DoGreedyMatchmakingResult doGreedyMatchmaking(VersionModeModel modeConfig,
                                                  List<MatchmakerRequestModel> requests,
                                                  List<MatchmakerMatchModel> matches,
                                                  List<MatchmakerMatchClientModel> clients);
}
