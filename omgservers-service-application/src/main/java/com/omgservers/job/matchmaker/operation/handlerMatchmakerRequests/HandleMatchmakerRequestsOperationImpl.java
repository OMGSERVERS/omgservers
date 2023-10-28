package com.omgservers.job.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.job.matchmaker.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.tenant.TenantModule;
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
    public Uni<Void> handleMatchmakerRequests(final Long matchmakerId,
                                              final MatchmakerState matchmakerState,
                                              final MatchmakerChangeOfState changeOfState) {
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var tenantId = matchmaker.getTenantId();
                    final var versionId = matchmaker.getVersionId();
                    return getVersionConfig(tenantId, versionId)
                            .flatMap(versionConfig -> executeMatchmaker(matchmakerState,
                                    changeOfState,
                                    versionConfig)
                            );
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long id) {
        final var request = new GetMatchmakerRequest(id);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<VersionConfigModel> getVersionConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionConfig(request)
                .map(GetVersionConfigResponse::getVersionConfig);
    }

    Uni<Void> executeMatchmaker(final MatchmakerState matchmakerState,
                                final MatchmakerChangeOfState changeOfState,
                                final VersionConfigModel versionConfig) {
        final var requests = matchmakerState.getRequests();

        if (requests.isEmpty()) {
            return Uni.createFrom().voidItem();
        } else {
            return doMatchmaking(versionConfig,
                    matchmakerState,
                    changeOfState);
        }
    }

    Uni<Void> doMatchmaking(final VersionConfigModel versionConfig,
                            final MatchmakerState matchmakerState,
                            final MatchmakerChangeOfState changeOfState) {

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {

                    final var requests = matchmakerState.getRequests().stream()
                            .collect(Collectors.groupingBy(RequestModel::getMode));

                    final var matches = matchmakerState.getMatches().stream()
                            // Filter out all stopped matches from matchmaking
                            .filter(match -> match.getStopped().equals(Boolean.FALSE))
                            .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

                    final var matchClients = matchmakerState.getMatchClients().stream()
                            .collect(Collectors.groupingBy(
                                    matchClient -> matchClient.getConfig().getRequest().getMode()));

                    requests.forEach((modeName, modeRequests) -> {
                        final var modeMatches = matches.getOrDefault(modeName, new ArrayList<>());
                        final var modeMatchClients = matchClients.getOrDefault(modeName, new ArrayList<>());

                        final var modeConfigOptional = versionConfig.getModes().stream()
                                .filter(mode -> mode.getName().equals(modeName)).findFirst();
                        if (modeConfigOptional.isPresent()) {
                            final var modeConfig = modeConfigOptional.get();
                            final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                                    modeConfig,
                                    modeRequests,
                                    modeMatches,
                                    modeMatchClients);
                            changeOfState.getCreatedMatches()
                                    .addAll(greedyMatchmakingResult.createdMatches());
                            changeOfState.getCreatedMatchClients()
                                    .addAll(greedyMatchmakingResult.createdMatchClients());
                        } else {
                            log.warn("Matchmaker requests with unknown mode were found, mode={}, requests={}",
                                    modeName, modeRequests.size());
                        }

                        changeOfState.getCompletedRequests().addAll(modeRequests);
                    });
                });
    }
}
