package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;

import java.util.List;
import java.util.Map;

public record IndexMatchmakerResult(Map<String, List<MatchmakerRequestModel>> requestsByMode,
                                    Map<String, List<MatchmakerMatchResourceModel>> matchResourcesByMode,
                                    Map<String, List<MatchmakerMatchAssignmentModel>> matchAssignmentsByMode) {
}
