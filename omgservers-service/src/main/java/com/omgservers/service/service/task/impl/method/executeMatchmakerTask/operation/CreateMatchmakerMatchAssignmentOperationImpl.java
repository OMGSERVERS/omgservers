package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateMatchmakerMatchAssignmentOperationImpl implements CreateMatchmakerMatchAssignmentOperation {

    final MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;

    @Override
    public MatchmakerMatchAssignmentModel execute(final MatchmakerMatchResourceModel matchmakerMatchResource,
                                                  final MatchmakerRequestModel matchmakerRequest,
                                                  final String groupName) {

        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();
        final var matchId = matchmakerMatchResource.getMatchId();

        final var clientId = matchmakerRequest.getClientId();

        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory
                .create(matchmakerId,
                        matchId,
                        clientId,
                        groupName,
                        new MatchmakerMatchAssignmentConfigDto(matchmakerRequest));

        return matchmakerMatchAssignment;
    }
}
