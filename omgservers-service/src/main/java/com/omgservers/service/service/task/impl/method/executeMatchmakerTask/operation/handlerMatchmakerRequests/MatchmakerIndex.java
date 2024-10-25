package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MatchmakerIndex {

    final Map<String, List<MatchmakerMatchAssignmentModel>> matchAssignmentsIndexByMode;
    final Map<String, List<MatchmakerMatchModel>> matchesIndexByMode;
    final Map<String, List<MatchmakerRequestModel>> requestsIndexByMode;

    public MatchmakerIndex(final MatchmakerStateDto matchmakerState) {
        requestsIndexByMode = matchmakerState.getMatchmakerRequests().stream()
                .collect(Collectors.groupingBy(MatchmakerRequestModel::getMode));

        matchesIndexByMode = matchmakerState.getMatchmakerMatches().stream()
                .collect(Collectors.groupingBy(matchmakerMatch ->
                        matchmakerMatch.getConfig().getModeConfig().getName()));

        matchAssignmentsIndexByMode = matchmakerState.getMatchmakerMatchAssignments().stream()
                .collect(Collectors.groupingBy(matchmakerMatchAssignment ->
                        matchmakerMatchAssignment.getConfig().getRequest().getMode()));
    }
}
