package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker.operation.handleEndedMatches.HandleEndedMatchesOperation;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
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
        return matchmakerModule.getShortcutService().getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    if (matchmaker.getDeleted()) {
                        log.info("Matchmaker was deleted, cancel job execution, id={}", matchmakerId);
                        return Uni.createFrom().item(false);
                    } else {
                        return handleMatchmaker(matchmaker)
                                .replaceWith(true);
                    }
                });
    }

    Uni<Void> handleMatchmaker(final MatchmakerModel matchmaker) {
        return Uni.createFrom().item(matchmaker.getId())
                .emitOn(Infrastructure.getDefaultWorkerPool())
                // TODO: get full state only first time, next use caching approach
                // Step 1. Getting current matchmaker state
                .flatMap(matchmakerId -> matchmakerModule.getShortcutService().getMatchmakerState(matchmakerId)
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
                        .flatMap(changeOfState -> matchmakerModule
                                .getShortcutService().updateMatchmakerState(matchmakerId, changeOfState)
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
