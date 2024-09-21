package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.doGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;

import java.util.List;

public interface DoGreedyMatchmakingOperation {
    DoGreedyMatchmakingResult doGreedyMatchmaking(Long matchmakerId,
                                                  TenantVersionModeDto config,
                                                  List<MatchmakerRequestModel> requests,
                                                  List<MatchmakerMatchModel> matches,
                                                  List<MatchmakerMatchClientModel> clients);
}
