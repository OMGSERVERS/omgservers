package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteGreedyMatchmakingOperationImpl implements ExecuteGreedyMatchmakingOperation {

    final MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;
    final MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                        final TenantVersionModeDto modeConfig,
                        final List<MatchmakerMatchModel> matchmakerMatches,
                        final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments,
                        final List<MatchmakerRequestModel> matchmakerRequests) {
        final var matchmaker = matchmakerState.getMatchmaker();

        final var greedyMatchmaker = new GreedyMatchmaker(modeConfig);
        matchmakerMatches.forEach(greedyMatchmaker::addMatchmakerMatch);
        matchmakerMatchAssignments.forEach(greedyMatchmaker::addMatchmakerMatchAssignment);

        matchmakerRequests.forEach(matchmakerRequest -> {
            if (!matchRequest(greedyMatchmaker, matchmakerRequest)) {
                // No match was found; creating a new one.
                final var matchmakerMatch = createMatchmakerMatch(matchmaker, modeConfig);
                greedyMatchmaker.addMatchmakerMatch(matchmakerMatch);

                if (!matchRequest(greedyMatchmaker, matchmakerRequest)) {
                    // It wasn't matched with new empty match
                    matchmakerChangeOfState.getRequestsToDelete().add(matchmakerRequest);
                }
            }
        });

        greedyMatchmaker.getReadyMatches().stream()
                .map(GreedyMatch::getMatchmakerMatch)
                .filter(Predicate.not(matchmakerMatches::contains))
                .forEach(matchmakerMatch -> matchmakerChangeOfState.getMatchesToSync().add(matchmakerMatch));

        // Queue match assignments to be created exclusively for open matches.
        greedyMatchmaker.getReadyMatches().stream()
                .filter(greedyMatch -> greedyMatch.getMatchmakerMatch().getStatus()
                        .equals(MatchmakerMatchStatusEnum.OPENED))
                .flatMap(greedyMatch -> greedyMatch.getMatchmakerMatchAssignments().stream())
                .filter(Predicate.not(matchmakerMatchAssignments::contains))
                .forEach(matchmakerMatchAssignment -> {
                    matchmakerChangeOfState.getMatchAssignmentsToSync().add(matchmakerMatchAssignment);
                    matchmakerChangeOfState.getRequestsToDelete()
                            .add(matchmakerMatchAssignment.getConfig().getRequest());
                });
    }

    MatchmakerMatchModel createMatchmakerMatch(final MatchmakerModel matchmaker,
                                               final TenantVersionModeDto modeConfig) {
        final var matchmakerId = matchmaker.getId();
        final var config = new MatchmakerMatchConfigDto(modeConfig);
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmakerId, config);
        return matchmakerMatch;
    }

    MatchmakerMatchAssignmentModel createMatchmakerMatchAssignment(final MatchmakerRequestModel matchmakerRequest,
                                                                   final MatchmakerMatchModel matchmakerMatch,
                                                                   final String groupName) {
        final var matchmakerId = matchmakerRequest.getMatchmakerId();
        final var userId = matchmakerRequest.getUserId();
        final var clientId = matchmakerRequest.getClientId();
        final var matchmakerMatchId = matchmakerMatch.getId();
        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory
                .create(matchmakerId,
                        matchmakerMatchId,
                        userId,
                        clientId,
                        groupName,
                        new MatchmakerMatchAssignmentConfigDto(matchmakerRequest));
        return matchmakerMatchAssignment;
    }

    Boolean matchRequest(final GreedyMatchmaker greedyMatchmaker,
                         final MatchmakerRequestModel matchmakerRequest) {
        return greedyMatchmaker.matchRequest(matchmakerRequest)
                .map(greedyResult -> {
                    final var matchmakerMatchAssignment =
                            createMatchmakerMatchAssignment(matchmakerRequest,
                                    greedyResult.greedyMatch().getMatchmakerMatch(),
                                    greedyResult.greedyGroup().config.getName());
                    greedyMatchmaker.addMatchmakerMatchAssignment(matchmakerMatchAssignment);

                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }
}
