package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking.ExecuteGreedyMatchmakingOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleMatchmakerRequestsOperationImpl implements HandleMatchmakerRequestsOperation {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    final ExecuteGreedyMatchmakingOperation executeGreedyMatchmaking;

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState,
                        final TenantVersionConfigDto tenantVersionConfig) {
        // Skip the operation if there are no matchmaking requests
        final var matchmakerRequests = matchmakerState.getMatchmakerRequests();
        if (matchmakerRequests.isEmpty()) {
            return;
        }

        final var matchmakerIndex = new MatchmakerIndex(matchmakerState);

        // Perform matchmaking for each mode.
        matchmakerIndex.requestsIndexByMode.forEach((modeName, modeRequests) -> {
            final var modeMatches = matchmakerIndex
                    .getMatchesIndexByMode()
                    .getOrDefault(modeName, new ArrayList<>());

            final var modeAssignments = matchmakerIndex
                    .getMatchAssignmentsIndexByMode()
                    .getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = tenantVersionConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName))
                    .findFirst();
            if (modeConfigOptional.isEmpty()) {
                log.warn("An unknown mode was requested for matchmaking, mode={}, matchmakerRequests={}",
                        modeName, modeRequests.size());
                matchmakerChangeOfState.getRequestsToDelete().addAll(modeRequests);
                return;
            }
            final var modeConfig = modeConfigOptional.get();

            executeGreedyMatchmaking.execute(matchmakerState,
                    matchmakerChangeOfState,
                    modeConfig,
                    modeMatches,
                    modeAssignments,
                    modeRequests);
        });
    }
}
