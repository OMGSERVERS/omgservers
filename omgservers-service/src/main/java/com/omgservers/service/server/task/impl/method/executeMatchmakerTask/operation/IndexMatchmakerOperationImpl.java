package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.IndexMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class IndexMatchmakerOperationImpl implements IndexMatchmakerOperation {

    @Override
    public IndexMatchmakerResult execute(final FetchMatchmakerResult fetchMatchmakerResult) {
        final var matchmakerRequests = fetchMatchmakerResult.matchmakerState().getMatchmakerRequests();
        final var requestsByMode = matchmakerRequests.stream()
                .collect(Collectors.groupingBy(MatchmakerRequestModel::getMode));

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();

        final var matchResourcesByMode = matchmakerState.getMatchmakerMatchResources().stream()
                .filter(matchmakerMatchResource -> {
                    final var status = matchmakerMatchResource.getStatus();
                    return status.equals(MatchmakerMatchResourceStatusEnum.PENDING) ||
                            status.equals(MatchmakerMatchResourceStatusEnum.CREATED);
                })
                .collect(Collectors.groupingBy(MatchmakerMatchResourceModel::getMode));

        final var matchAssignmentsByMode = matchmakerState.getMatchmakerMatchAssignments().stream()
                .collect(Collectors.groupingBy(matchmakerMatchAssignment ->
                        matchmakerMatchAssignment.getConfig().getMatchmakerRequest().getMode()));

        return new IndexMatchmakerResult(requestsByMode, matchResourcesByMode, matchAssignmentsByMode);
    }
}
