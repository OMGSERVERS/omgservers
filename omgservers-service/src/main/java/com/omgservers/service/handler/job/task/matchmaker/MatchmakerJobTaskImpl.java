package com.omgservers.service.handler.job.task.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.handler.job.task.matchmaker.operation.handleEndedMatches.HandleEndedMatchesOperation;
import com.omgservers.service.handler.job.task.matchmaker.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.handler.job.task.matchmaker.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
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
                        .flatMap(matchmakerState -> {
                            final var changeOfState = new MatchmakerChangeOfState();
                            // Step 2. Handling matchmaker commands
                            return handleMatchmakerCommands(matchmakerState, changeOfState)
                                    // Step 3. Handling ended matched
                                    .invoke(voidItem -> handleEndedMatchesOperation
                                            .handleEndedMatches(matchmakerState, changeOfState))
                                    // Step 4. Handling matchmaker requests
                                    .flatMap(voidItem -> handleMatchmakerRequestsOperation.handleMatchmakerRequests(
                                            matchmakerId,
                                            matchmakerState,
                                            changeOfState))
                                    .replaceWith(changeOfState);
                        })
                        // Step 5. Updating matchmaker state
                        .flatMap(changeOfState -> updateMatchmakerState(matchmakerId, changeOfState)
                                .invoke(udpated -> {
                                    if (changeOfState.isNotEmpty()) {
                                        log.info("Matchmaker was executed, id={}, " +
                                                        "completedMatchmakerCommands={}, " +
                                                        "completedRequests={}, " +
                                                        "createdMatches={}, " +
                                                        "updatedMatches={}, " +
                                                        "endedMatches={}, " +
                                                        "createdMatchClients={}, " +
                                                        "orphanedMatchClients={}",
                                                matchmakerId,
                                                changeOfState.getCompletedMatchmakerCommands().size(),
                                                changeOfState.getCompletedRequests().size(),
                                                changeOfState.getCreatedMatches().size(),
                                                changeOfState.getStoppedMatches().size(),
                                                changeOfState.getEndedMatches().size(),
                                                changeOfState.getCreatedMatchClients().size(),
                                                changeOfState.getOrphanedMatchClients().size());
                                    }
                                }))
                )
                .replaceWithVoid();
    }

    Uni<MatchmakerState> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                       final MatchmakerChangeOfState changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerModule.getMatchmakerService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }

    Uni<Void> handleMatchmakerCommands(final MatchmakerState matchmakerState,
                                       final MatchmakerChangeOfState changeOfState) {
        final var matchmakerCommands = matchmakerState.getMatchmakerCommands();
        return Multi.createFrom().iterable(matchmakerCommands)
                .onItem().transformToUniAndConcatenate(matchmakerCommand -> handleMatchmakerCommandOperation
                        .handleMatchmakerCommand(matchmakerState, changeOfState, matchmakerCommand)
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
                .invoke(results -> changeOfState.getCompletedMatchmakerCommands().addAll(matchmakerCommands))
                .replaceWithVoid();
    }
}
