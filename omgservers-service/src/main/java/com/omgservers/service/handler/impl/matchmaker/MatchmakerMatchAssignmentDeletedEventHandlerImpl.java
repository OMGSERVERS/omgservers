package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.body.RemoveClientRuntimeCommandBodyDto;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.deployment.CreateDeploymentRequestOperation;
import com.omgservers.service.shard.match.MatchShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchAssignmentDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final MatchShard matchShard;

    final CreateDeploymentRequestOperation createDeploymentRequestOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchAssignmentDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchmakerMatchAssignmentId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatchmakerMatchAssignment(matchmakerId, matchmakerMatchAssignmentId)
                .flatMap(matchmakerMatchAssignment -> {
                    log.debug("Deleted, {}", matchmakerMatchAssignment);

                    final var matchId = matchmakerMatchAssignment.getMatchId();

                    return getMatch(matchId)
                            .flatMap(match -> {
                                final var runtimeId = match.getRuntimeId();
                                final var clientId = matchmakerMatchAssignment.getClientId();

                                return createRemoveClientRuntimeCommand(runtimeId, clientId, idempotencyKey)
                                        .flatMap(created -> createDeploymentRequestOperation.execute(clientId,
                                                idempotencyKey));
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchAssignmentModel> getMatchmakerMatchAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchAssignmentRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerMatchAssignmentResponse::getMatchmakerMatchAssignment);
    }

    Uni<MatchModel> getMatch(final Long id) {
        final var request = new GetMatchRequest(id);
        return matchShard.getService().execute(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> createRemoveClientRuntimeCommand(final Long runtimeId,
                                                  final Long clientId,
                                                  final String idempotencyKey) {
        final var commandBody = new RemoveClientRuntimeCommandBodyDto(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, commandBody, idempotencyKey);

        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }
}
