package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.component.MatchmakerMatcher;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;

import java.util.List;

public interface ExecuteMatchmakerMatcherOperation {
    void execute(FetchMatchmakerResult fetchMatchmakerResult,
                 HandleMatchmakerResult handleMatchmakerResult,
                 List<MatchmakerRequestModel> matchmakerRequests,
                 MatchmakerMatcher matchmakerMatcher);
}
