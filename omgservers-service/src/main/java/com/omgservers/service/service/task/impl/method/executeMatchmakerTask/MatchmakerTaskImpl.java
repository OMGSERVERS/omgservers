package com.omgservers.service.service.task.impl.method.executeMatchmakerTask;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEndedMatches.HandleEndedMatchesOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerTaskImpl {

    final MatchmakerModule matchmakerModule;

    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;
    final HandleMatchmakerCommandOperation handleMatchmakerCommandOperation;
    final HandleEndedMatchesOperation handleEndedMatchesOperation;

    public Uni<Boolean> executeTask(final Long matchmakerId) {
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> handleMatchmaker(matchmaker)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Void> handleMatchmaker(final MatchmakerModel matchmaker) {
        return Uni.createFrom().item(matchmaker.getId())
                .emitOn(Infrastructure.getDefaultWorkerPool())
                // TODO: get full state only first time, next use caching approach
                // Step 1. Getting current matchmaker state
                .flatMap(matchmakerId -> getMatchmakerState(matchmakerId)
                        .flatMap(currentState -> {
                            final var changeOfState = new MatchmakerChangeOfStateDto();
                            // Step 2. Handling matchmaker commands
                            return handleMatchmakerCommands(currentState, changeOfState)
                                    // Step 3. Handling ended matched
                                    .invoke(voidItem -> handleEndedMatchesOperation
                                            .handleEndedMatches(currentState, changeOfState))
                                    // Step 4. Handling matchmaker requests
                                    .flatMap(voidItem -> handleMatchmakerRequestsOperation.handleMatchmakerRequests(
                                            matchmaker,
                                            currentState,
                                            changeOfState))
                                    .replaceWith(changeOfState);
                        })
                        // Step 5. Updating matchmaker state
                        .flatMap(changeOfState -> updateMatchmakerState(matchmakerId, changeOfState)
                                .invoke(updated -> {
                                    if (changeOfState.isNotEmpty()) {
                                        log.info("Matchmaker was executed, id={}, " +
                                                        "commandsToDelete={}, " +
                                                        "requestsToDelete={}, " +
                                                        "matchesToSync={}, " +
                                                        "matchesToUpdateStatus={}, " +
                                                        "matchesToDelete={}, " +
                                                        "clientsToSync={}, " +
                                                        "clientsToDelete={}",
                                                matchmakerId,
                                                changeOfState.getCommandsToDelete().size(),
                                                changeOfState.getRequestsToDelete().size(),
                                                changeOfState.getMatchesToSync().size(),
                                                changeOfState.getMatchesToUpdateStatus().size(),
                                                changeOfState.getMatchesToDelete().size(),
                                                changeOfState.getClientsToSync().size(),
                                                changeOfState.getClientsToDelete().size());
                                    }
                                }))
                )
                .replaceWithVoid();
    }

    Uni<MatchmakerStateDto> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerStateDto);
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                       final MatchmakerChangeOfStateDto changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerModule.getService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }

    Uni<Void> handleMatchmakerCommands(final MatchmakerStateDto currentState,
                                       final MatchmakerChangeOfStateDto changeOfState) {
        final var matchmakerCommands = currentState.getCommands();
        return Multi.createFrom().iterable(matchmakerCommands)
                .onItem().transformToUniAndConcatenate(matchmakerCommand -> handleMatchmakerCommandOperation
                        .handleMatchmakerCommand(currentState, changeOfState, matchmakerCommand)
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.warn("Handle matchmaker command failed, " +
                                            "matchmakerCommand={}/{}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    matchmakerCommand.getMatchmakerId(), matchmakerCommand.getId(),
                                    matchmakerCommand.getQualifier(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return null;
                        })
                        .replaceWithVoid()
                )
                .collect().asList()
                .invoke(results -> changeOfState.getCommandsToDelete().addAll(matchmakerCommands))
                .replaceWithVoid();
    }
}
