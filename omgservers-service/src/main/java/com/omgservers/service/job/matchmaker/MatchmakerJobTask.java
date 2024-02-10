package com.omgservers.service.job.matchmaker;

import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.job.matchmaker.operation.handleEndedMatches.HandleEndedMatchesOperation;
import com.omgservers.service.job.matchmaker.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.job.matchmaker.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.jobService.impl.JobTask;
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
public class MatchmakerJobTask implements JobTask {

    final MatchmakerModule matchmakerModule;

    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;
    final HandleMatchmakerCommandOperation handleMatchmakerCommandOperation;
    final HandleEndedMatchesOperation handleEndedMatchesOperation;

    @Override
    public JobQualifierEnum getJobQualifier() {
        return JobQualifierEnum.MATCHMAKER;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var matchmakerId = entityId;

        return matchmakerModule.getShortcutService().getMatchmaker(matchmakerId)
                .map(matchmaker -> {
                    if (matchmaker.getDeleted()) {
                        log.info("Matchmaker was deleted, skip job execution, id={}", matchmakerId);
                        return null;
                    } else {
                        return matchmaker;
                    }
                })
                .emitOn(Infrastructure.getDefaultWorkerPool())
                // TODO: get full state only first time, next use caching approach
                // Step 1. Getting current matchmaker state
                .onItem().ifNotNull().transformToUni(matchmaker -> matchmakerModule
                        .getShortcutService().getMatchmakerState(matchmakerId)
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
                                .invoke(voidItem -> {
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
