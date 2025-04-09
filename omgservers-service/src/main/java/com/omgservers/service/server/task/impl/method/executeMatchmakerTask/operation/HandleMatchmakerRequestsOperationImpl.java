package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
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

    final ExecuteMatchmakerMatcherOperation executeMatchmakerMatcherOperation;
    final CreateMatchmakerMatcherOperation createMatchmakerMatcherOperation;
    final IndexMatchmakerOperation indexMatchmakerOperation;

    @Override
    public void execute(final FetchMatchmakerResult fetchMatchmakerResult,
                        final HandleMatchmakerResult handleMatchmakerResult,
                        final TenantVersionConfigDto tenantVersionConfig) {
        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        // Skip the operation if there are no matchmaking requests
        final var matchmakerRequests = fetchMatchmakerResult.matchmakerState().getMatchmakerRequests();
        if (matchmakerRequests.isEmpty()) {
            return;
        }

        final var indexMatchmakerResult = indexMatchmakerOperation.execute(fetchMatchmakerResult);

        // Perform matchmaking for each mode.
        for (final String modeName : indexMatchmakerResult.requestsByMode().keySet()) {
            final var modeRequests = indexMatchmakerResult.requestsByMode().get(modeName);

            final var modeMatchResources = indexMatchmakerResult.matchResourcesByMode()
                    .getOrDefault(modeName, new ArrayList<>());

            final var modeAssignments = indexMatchmakerResult.matchAssignmentsByMode()
                    .getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = tenantVersionConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName))
                    .findFirst();
            if (modeConfigOptional.isEmpty()) {
                log.warn("Unknown mode \"{}\" was requested {} times, removing such requests",
                        modeName, modeRequests.size());
                final var modeRequestIds = modeRequests.stream().map(MatchmakerRequestModel::getId).toList();
                handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerRequestsToDelete()
                        .addAll(modeRequestIds);
            } else {
                final var modeConfig = modeConfigOptional.get();

                final var matchmakerMatcher = createMatchmakerMatcherOperation.execute(modeConfig,
                        modeMatchResources,
                        modeAssignments);

                executeMatchmakerMatcherOperation.execute(matchmakerId,
                        matchmakerMatcher,
                        handleMatchmakerResult,
                        modeRequests);
            }
        }
    }
}
