package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerCommandsOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerMatchAssignmentsOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerMatchResourcesOperation;
import com.omgservers.service.operation.matchmaker.DeleteMatchmakerRequestsOperation;
import com.omgservers.service.operation.task.DeleteTaskOperation;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;

    final DeleteMatchmakerMatchAssignmentsOperation deleteMatchmakerMatchAssignmentsOperation;
    final DeleteMatchmakerMatchResourcesOperation deleteMatchmakerMatchResourcesOperation;
    final DeleteMatchmakerCommandsOperation deleteMatchmakerCommandsOperation;
    final DeleteMatchmakerRequestsOperation deleteMatchmakerRequestsOperation;

    final DeleteTaskOperation deleteTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        return getMatchmakerState(matchmakerId)
                .flatMap(matchmakerState -> {
                    final var matchmaker = matchmakerState.getMatchmaker();
                    log.debug("Deleted, {}", matchmaker);

                    return deleteTaskOperation.execute(matchmakerId)
                            .flatMap(deleted -> {
                                final var matchmakerCommands = matchmakerState.getMatchmakerCommands();
                                final var matchmakerRequests = matchmakerState.getMatchmakerRequests();
                                final var matchmakerMatchResources = matchmakerState.getMatchmakerMatchResources();
                                final var matchmakerMatchAssignments = matchmakerState.getMatchmakerMatchAssignments();

                                if (!matchmakerCommands.isEmpty() ||
                                        !matchmakerRequests.isEmpty() ||
                                        !matchmakerMatchResources.isEmpty() ||
                                        !matchmakerMatchAssignments.isEmpty()) {
                                    log.error("Matchmaker \"{}\" deleted, but some data remains, " +
                                                    "commands={}, requests={}, matchResources={}, matchAssignments={}",
                                            matchmakerId,
                                            matchmakerCommands.size(),
                                            matchmakerRequests.size(),
                                            matchmakerMatchResources.size(),
                                            matchmakerMatchAssignments.size());

                                    return deleteMatchmakerCommandsOperation.execute(matchmakerId)
                                            .flatMap(voidItem ->
                                                    deleteMatchmakerRequestsOperation.execute(matchmakerId))
                                            .flatMap(voidItem ->
                                                    deleteMatchmakerMatchResourcesOperation.execute(matchmakerId))
                                            .flatMap(voidItem ->
                                                    deleteMatchmakerMatchAssignmentsOperation.execute(matchmakerId));
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerStateDto> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }
}
