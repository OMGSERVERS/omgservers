package com.omgservers.service.job.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.service.exception.ServerSideClientExceptionException;
import com.omgservers.service.exception.ServerSideNotFoundException;
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

import java.util.Objects;

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

        return getMatchmaker(matchmakerId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .invoke(runtime -> {
                    if (Objects.isNull(runtime)) {
                        log.warn("Matchmaker was not found, skip job execution, " +
                                "matchmakerId={}", matchmakerId);
                    }
                })
                .emitOn(Infrastructure.getDefaultWorkerPool())
                //TODO: get full state only first time, next use caching approach
                // Step 1. Getting current matchmaker state
                .onItem().ifNotNull().transformToUni(matchmaker -> getMatchmakerState(matchmakerId)
                        .flatMap(voidItem -> getMatchmakerState(matchmakerId))
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
                                .invoke(voidItem -> {
                                    if (changeOfState.isNotEmpty()) {
                                        log.info("Matchmaker was executed, matchmakerId={}, " +
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

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchmakerState> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }

    Uni<Void> handleMatchmakerCommands(final MatchmakerState matchmakerState,
                                       final MatchmakerChangeOfState changeOfState) {
        final var matchmakerCommands = matchmakerState.getMatchmakerCommands();
        return Multi.createFrom().iterable(matchmakerCommands)
                .onItem().transformToUniAndConcatenate(matchmakerCommand -> handleMatchmakerCommandOperation
                        .handleMatchmakerCommand(matchmakerState, changeOfState, matchmakerCommand)
                        .onFailure(ServerSideClientExceptionException.class)
                        .recoverWithNull().replaceWithVoid())
                .collect().asList()
                .invoke(results -> changeOfState.getCompletedMatchmakerCommands().addAll(matchmakerCommands))
                .replaceWithVoid();
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId, MatchmakerChangeOfState changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerModule.getMatchmakerService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }
}
