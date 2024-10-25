package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;

import java.util.List;

public interface ExecuteGreedyMatchmakingOperation {
    void execute(MatchmakerStateDto matchmakerState,
                 MatchmakerChangeOfStateDto matchmakerChangeOfState,
                 TenantVersionModeDto modeConfig,
                 List<MatchmakerMatchModel> matchmakerMatches,
                 List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments,
                 List<MatchmakerRequestModel> matchmakerRequests);
}
