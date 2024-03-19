package com.omgservers.service.handler.job.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.service.handler.job.matchmaker.operation.handleEndedMatches.HandleEndedMatchesOperation;
import com.omgservers.service.handler.job.matchmaker.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.handler.job.matchmaker.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
public class MatchmakerJobTaskImpl {

    final MatchmakerModule matchmakerModule;

    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;
    final HandleMatchmakerCommandOperation handleMatchmakerCommandOperation;
    final HandleEndedMatchesOperation handleEndedMatchesOperation;

    public Uni<Boolean> executeTask(final Long matchmakerId) {
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    if (matchmaker.getDeleted()) {
                        log.info("Matchmaker was deleted, cancel job execution, id={}", matchmakerId);
                        return Uni.createFrom().item(Boolean.FALSE);
                    } else {
                        return handleMatchmaker(matchmaker)
                                .replaceWith(Boolean.TRUE);
                    }
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Void> handleMatchmaker(final MatchmakerModel matchmaker) {
        return Uni.createFrom().item(matchmaker.getId())
                .emitOn(Infrastructure.getDefaultWorkerPool())
                // TODO: get full state only first time, next use caching approach
                // Step 1. Getting current matchmaker state
                .flatMap(matchmakerId -> getMatchmakerState(matchmakerId)
                        .flatMap(currentState -> {
                            final var changeOfState = new MatchmakerChangeOfStateModel();
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

    Uni<MatchmakerStateModel> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerStateModel);
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                       final MatchmakerChangeOfStateModel changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerModule.getMatchmakerService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }

    Uni<Void> handleMatchmakerCommands(final MatchmakerStateModel currentState,
                                       final MatchmakerChangeOfStateModel changeOfState) {
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
