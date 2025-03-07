package com.omgservers.service.service.task.impl.method.executeMatchmakerTask;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.getTenantVersionConfig.GetTenantVersionConfigOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleClosedMatches.HandleClosedMatchesOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEmptyMatchesOperation.HandleEmptyMatchesOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.HandleMatchmakerCommandsOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests.HandleMatchmakerRequestsOperation;
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

    final MatchmakerShard matchmakerShard;

    final HandleMatchmakerCommandsOperation handleMatchmakerCommandsOperation;
    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;
    final HandleMatchmakerCommandOperation handleMatchmakerCommandOperation;
    final GetTenantVersionConfigOperation getTenantVersionConfigOperation;
    final HandleClosedMatchesOperation handleClosedMatchesOperation;
    final HandleEmptyMatchesOperation handleEmptyMatchesOperation;

    public Uni<Boolean> execute(final Long matchmakerId) {
        // TODO: Using a caching approach
        return getMatchmakerState(matchmakerId)
                .flatMap(matchmakerState -> {
                    final var matchmaker = matchmakerState.getMatchmaker();
                    return getTenantVersionConfigOperation.execute(matchmaker)
                            .emitOn(Infrastructure.getDefaultWorkerPool())
                            .flatMap(tenantVersionConfig -> {
                                final var matchmakerChangeOfState = new MatchmakerChangeOfStateDto();

                                handleMatchmakerCommandsOperation.execute(matchmakerState, matchmakerChangeOfState);
                                handleClosedMatchesOperation.execute(matchmakerState, matchmakerChangeOfState);
                                handleEmptyMatchesOperation.execute(matchmakerState, matchmakerChangeOfState);
                                handleMatchmakerRequestsOperation.execute(matchmakerState,
                                        matchmakerChangeOfState,
                                        tenantVersionConfig);

                                return updateMatchmakerState(matchmakerId, matchmakerChangeOfState)
                                        .invoke(updated -> {
                                            if (matchmakerChangeOfState.isNotEmpty()) {
                                                log.debug("Matchmaker task was executed, matchmakerId={}, {}",
                                                        matchmakerId,
                                                        matchmakerChangeOfState);
                                            }

                                            final var matchesToSync = matchmakerChangeOfState
                                                    .getMatchesToSync();
                                            if (!matchesToSync.isEmpty()) {
                                                log.info("The \"{}\" matches/s were created by matchmaker {}",
                                                        matchesToSync.size(), matchmakerId);
                                            }
                                        });
                            });
                })
                .replaceWith(Boolean.TRUE);
    }

    Uni<MatchmakerStateDto> getMatchmakerState(final Long matchmakerId) {
        final var request = new GetMatchmakerStateRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerStateResponse::getMatchmakerState);
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                       final MatchmakerChangeOfStateDto changeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, changeOfState);
        return matchmakerShard.getService().execute(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }
}
