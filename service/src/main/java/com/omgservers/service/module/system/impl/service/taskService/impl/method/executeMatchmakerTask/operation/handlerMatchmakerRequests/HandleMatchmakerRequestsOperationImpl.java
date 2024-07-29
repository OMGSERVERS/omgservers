package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.module.tenant.GetVersionConfigRequest;
import com.omgservers.schema.module.tenant.GetVersionConfigResponse;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleMatchmakerRequestsOperationImpl implements HandleMatchmakerRequestsOperation {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;

    @Override
    public Uni<Void> handleMatchmakerRequests(final MatchmakerModel matchmaker,
                                              final MatchmakerStateModel currentState,
                                              final MatchmakerChangeOfStateModel changeOfState) {
        final var matchmakerId = matchmaker.getId();
        final var tenantId = matchmaker.getTenantId();
        final var versionId = matchmaker.getVersionId();
        // TODO cache version config and reuse each of iteration
        return getVersionConfig(tenantId, versionId)
                .invoke(versionConfig -> executeMatchmaker(matchmakerId,
                        currentState,
                        changeOfState,
                        versionConfig))
                .replaceWithVoid();
    }

    Uni<VersionConfigModel> getVersionConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionConfig(request)
                .map(GetVersionConfigResponse::getVersionConfig);
    }

    void executeMatchmaker(final Long matchmakerId,
                           final MatchmakerStateModel currentState,
                           final MatchmakerChangeOfStateModel changeOfState,
                           final VersionConfigModel versionConfig) {
        final var requests = currentState.getRequests();

        if (!requests.isEmpty()) {
            doMatchmaking(matchmakerId, currentState, changeOfState, versionConfig);
        }
    }

    void doMatchmaking(final Long matchmakerId,
                       final MatchmakerStateModel currentState,
                       final MatchmakerChangeOfStateModel changeOfState,
                       final VersionConfigModel versionConfig) {

        final var requests = currentState.getRequests().stream()
                .collect(Collectors.groupingBy(MatchmakerRequestModel::getMode));

        final var matches = currentState.getMatches().stream()
                // Use only prepared matches for matchmaking
                .filter(match -> match.getStatus().equals(MatchmakerMatchStatusEnum.PREPARED))
                .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

        final var clients = currentState.getClients().stream()
                .collect(Collectors.groupingBy(client -> client.getConfig().getRequest().getMode()));

        final var createdMatches = currentState.getMatches().stream()
                .filter(match -> match.getStatus().equals(MatchmakerMatchStatusEnum.CREATED))
                .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

        // Do matchmaking for each mode
        requests.forEach((modeName, modeRequests) -> {
            final var modeMatches = matches.getOrDefault(modeName, new ArrayList<>());
            final var modeClients = clients.getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = versionConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName)).findFirst();
            if (modeConfigOptional.isPresent()) {
                final var modeConfig = modeConfigOptional.get();

                final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                        matchmakerId,
                        modeConfig,
                        modeRequests,
                        modeMatches,
                        modeClients);

                changeOfState.getRequestsToDelete()
                        .addAll(greedyMatchmakingResult.matchedRequests());

                final var currentCount = createdMatches.getOrDefault(modeName, new ArrayList<>()).size();
                final var requiredCount = greedyMatchmakingResult.createdMatches().size();
                if (requiredCount > currentCount) {
                    for (int i = 0; i < requiredCount - currentCount; i++) {
                        final var createdMatch = greedyMatchmakingResult.createdMatches().get(i);
                        changeOfState.getMatchesToSync().add(createdMatch);
                    }
                }

                changeOfState.getClientsToSync()
                        .addAll(greedyMatchmakingResult.createdClients());
            } else {
                log.warn("Matchmaker requests with unknown mode were found, mode={}, requests={}",
                        modeName, modeRequests.size());
                changeOfState.getRequestsToDelete().addAll(modeRequests);
            }
        });
    }
}
