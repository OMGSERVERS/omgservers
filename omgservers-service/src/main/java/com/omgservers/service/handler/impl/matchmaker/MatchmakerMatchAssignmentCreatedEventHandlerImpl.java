package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.body.AssignClientRuntimeCommandBodyDto;
import com.omgservers.schema.shard.match.GetMatchRequest;
import com.omgservers.schema.shard.match.GetMatchResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.deployment.CreateKickClientDeploymentCommandOperation;
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
public class MatchmakerMatchAssignmentCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final MatchShard matchShard;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    final CreateKickClientDeploymentCommandOperation createKickClientDeploymentCommandOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchAssignmentCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchmakerMatchAssignmentId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatchmakerMatchAssignment(matchmakerId, matchmakerMatchAssignmentId)
                .flatMap(matchmakerMatchAssignment -> {
                    log.debug("Created, {}", matchmakerMatchAssignment);

                    final var clientId = matchmakerMatchAssignment.getClientId();
                    return createKickClientDeploymentCommandOperation.execute(clientId, idempotencyKey)
                            .flatMap(created -> {
                                final var matchId = matchmakerMatchAssignment.getMatchId();
                                return getMatch(matchId)
                                        .flatMap(match -> {
                                            final var runtimeId = match.getRuntimeId();
                                            final var groupName = matchmakerMatchAssignment.getGroupName();

                                            return createAssignClientRuntimeCommand(runtimeId,
                                                    clientId,
                                                    groupName,
                                                    idempotencyKey);
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchAssignmentModel> getMatchmakerMatchAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchAssignmentRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerMatchAssignmentResponse::getMatchmakerMatchAssignment);
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchModel> getMatch(final Long id) {
        final var request = new GetMatchRequest(id);
        return matchShard.getService().execute(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> createAssignClientRuntimeCommand(final Long runtimeId,
                                                  final Long clientId,
                                                  final String groupName,
                                                  final String idempotencyKey) {
        final var commandBody = new AssignClientRuntimeCommandBodyDto();
        commandBody.setClientId(clientId);
        commandBody.setGroupName(groupName);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, commandBody, idempotencyKey);

        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }
}
