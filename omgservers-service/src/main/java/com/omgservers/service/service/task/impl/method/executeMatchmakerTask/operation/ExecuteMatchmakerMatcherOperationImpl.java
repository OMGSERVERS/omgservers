package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchResourceModelFactory;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.component.MatchmakerMatcher;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteMatchmakerMatcherOperationImpl implements ExecuteMatchmakerMatcherOperation {

    final CreateMatchmakerMatchAssignmentOperation createMatchmakerMatchAssignmentOperation;
    final MatchmakerMatchResourceModelFactory matchmakerMatchResourceModelFactory;

    @Override
    public void execute(final Long matchmakerId,
                        final MatchmakerMatcher matchmakerMatcher,
                        final HandleMatchmakerResult handleMatchmakerResult,
                        final List<MatchmakerRequestModel> matchmakerRequests) {

        for (final var matchmakerRequest : matchmakerRequests) {
            if (!matchRequest(matchmakerMatcher, matchmakerRequest)) {
                // No match was found; creating a new one.
                final var groupName = matchmakerMatcher.getConfig().getName();
                final var matchmakerMatchResource = matchmakerMatchResourceModelFactory.create(matchmakerId, groupName);
                matchmakerMatcher.addMatchmakerMatchResource(matchmakerMatchResource, true);

                if (!matchRequest(matchmakerMatcher, matchmakerRequest)) {
                    log.warn("Matchmaker request wasn't matched event with a newly created match, id={}/{}",
                            matchmakerRequest.getMatchmakerId(), matchmakerRequest.getId());
                    handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerRequestsToDelete()
                            .add(matchmakerRequest.getId());
                }
            }
        }

        final var matchmakerMatchResourcesToSync = matchmakerMatcher.getMatchmakerMatchResourcesToSync();
        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchResourcesToSync()
                .addAll(matchmakerMatchResourcesToSync);

        final var matchmakerMatchAssignmentsToSync = matchmakerMatcher.getMatchmakerMatchAssignmentsToSync();
        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchAssignmentsToSync()
                .addAll(matchmakerMatchAssignmentsToSync);

        final var matchmakerRequestsToDelete = matchmakerMatcher.getMatchmakerRequestsToDelete();
        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerRequestsToDelete()
                .addAll(matchmakerRequestsToDelete);
    }

    boolean matchRequest(final MatchmakerMatcher matchmakerMatcher,
                         final MatchmakerRequestModel matchmakerRequest) {
        return matchmakerMatcher.matchRequest(matchmakerRequest)
                .map(matchingResult -> {
                    final var matchmakerMatchAssignment = createMatchmakerMatchAssignmentOperation.execute(
                            matchingResult.matcherMatch().getMatchmakerMatchResource(),
                            matchmakerRequest,
                            matchingResult.matcherGroup().getConfig().getName());
                    matchmakerMatcher.addMatchmakerMatchAssignment(matchmakerMatchAssignment, true);

                    return true;
                })
                .orElse(false);
    }
}
