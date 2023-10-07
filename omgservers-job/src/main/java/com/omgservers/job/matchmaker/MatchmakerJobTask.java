package com.omgservers.job.matchmaker;

import com.omgservers.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.job.matchmaker.operation.handleMatchmakerCommands.HandleMatchmakerCommandsOperation;
import com.omgservers.job.matchmaker.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
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

    final HandleMatchmakerCommandsOperation handleMatchmakerCommandsOperation;
    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;

    @Override
    public JobQualifierEnum getJobType() {
        return JobQualifierEnum.MATCHMAKER;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        final var matchmakerId = entityId;
        //TODO: get full state only first time, next use caching approach
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                // Step 1. Get current matchmaker state
                .flatMap(voidItem -> getMatchmakerState(matchmakerId))
                .map(IndexedMatchmakerState::new)
                .flatMap(indexedMatchmakerState -> {
                    final var matchmakerChangeOfState = new MatchmakerChangeOfState();
                    // Step 2. Handle matchmaker commands
                    return handleMatchmakerCommandsOperation.handleMatchmakerCommands(
                                    matchmakerId,
                                    indexedMatchmakerState,
                                    matchmakerChangeOfState)
                            // Step 3. Handle matchmaker requests
                            .flatMap(voidItem -> handleMatchmakerRequestsOperation.handleMatchmakerRequests(
                                    matchmakerId,
                                    indexedMatchmakerState,
                                    matchmakerChangeOfState))
                            .replaceWith(matchmakerChangeOfState);
                })
                // Step 4. Store new matchmaker state
                .flatMap(matchmakerChangeOfState -> updateMatchmakerState(matchmakerId, matchmakerChangeOfState))
                .replaceWithVoid();
    }

    Uni<MatchmakerState> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmakerState(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId, MatchmakerChangeOfState matchmakerChangeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, matchmakerChangeOfState);
        return matchmakerModule.getMatchmakerService().updateMatchmakerState(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }
}
