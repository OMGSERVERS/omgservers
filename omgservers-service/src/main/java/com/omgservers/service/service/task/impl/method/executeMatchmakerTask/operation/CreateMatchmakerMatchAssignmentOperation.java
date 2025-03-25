package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;

public interface CreateMatchmakerMatchAssignmentOperation {
    MatchmakerMatchAssignmentModel execute(MatchmakerMatchResourceModel matchmakerMatch,
                                           MatchmakerRequestModel matchmakerRequest,
                                           String groupName);
}
