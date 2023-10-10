package com.omgservers.job.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.job.matchmaker.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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
                                              final IndexedMatchmakerState indexedMatchmakerState,
                                              final MatchmakerChangeOfState matchmakerChangeOfState) {
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var tenantId = matchmaker.getTenantId();
                    final var stageId = matchmaker.getStageId();
                    return getStageVersionId(tenantId, stageId)
                            .flatMap(versionId -> getVersionConfig(tenantId, versionId)
                                    .flatMap(versionConfig -> executeMatchmaker(
                                            matchmakerId,
                                            indexedMatchmakerState,
                                            matchmakerChangeOfState,
                                            tenantId,
                                            stageId,
                                            versionId,
                                            versionConfig)
                                    )
                            );
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long id) {
        final var request = new GetMatchmakerRequest(id);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Long> getStageVersionId(final Long tenantId, final Long stageId) {
        final var request = new GetStageVersionIdRequest(tenantId, stageId);
        return tenantModule.getVersionService().getStageVersionId(request)
                .map(GetStageVersionIdResponse::getVersionId);
    }

    Uni<VersionConfigModel> getVersionConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionConfig(request)
                .map(GetVersionConfigResponse::getVersionConfig);
    }

    Uni<Void> executeMatchmaker(final Long matchmakerId,
                                final IndexedMatchmakerState indexedMatchmakerState,
                                final MatchmakerChangeOfState matchmakerChangeOfState,
                                final Long tenantId,
                                final Long stageId,
                                final Long versionId,
                                final VersionConfigModel versionConfig) {
        final var requests = indexedMatchmakerState.getMatchmakerState().getRequests();
        final var matches = indexedMatchmakerState.getMatchmakerState().getMatches().stream()
                .filter(match -> match.getStopped().equals(Boolean.FALSE))
                .toList();

        if (requests.isEmpty()) {
            log.debug("There aren't any requests for matchmaking, matchmakerId={}", matchmakerId);
            return Uni.createFrom().voidItem();
        } else {
            log.info("Execute matchmaker, matchmakerId={}, requests={}, matches={}",
                    matchmakerId, requests.size(), matches.size());
            return doMatchmaking(
                    tenantId,
                    stageId,
                    versionId,
                    matchmakerId,
                    matchmakerChangeOfState,
                    requests,
                    matches,
                    versionConfig);
        }
    }

    Uni<Void> doMatchmaking(final Long tenantId,
                            final Long stageId,
                            final Long versionId,
                            final Long matchmakerId,
                            final MatchmakerChangeOfState matchmakerChangeOfState,
                            final List<RequestModel> matchmakerRequests,
                            final List<MatchModel> matchmakerMatches,
                            final VersionConfigModel versionConfig) {

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var groupedRequests = matchmakerRequests.stream()
                            .collect(Collectors.groupingBy(RequestModel::getMode));

                    final var groupedMatches = matchmakerMatches.stream()
                            .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

                    groupedRequests.forEach((modeName, modeRequests) -> {
                        final var modeMatches = groupedMatches.getOrDefault(modeName, new ArrayList<>());

                        final var modeConfigOptional = versionConfig.getModes().stream()
                                .filter(mode -> mode.getName().equals(modeName)).findFirst();
                        if (modeConfigOptional.isPresent()) {
                            final var modeConfig = modeConfigOptional.get();
                            // TODO: detect matchmaking type (default - greedy)
                            final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                                    tenantId,
                                    stageId,
                                    versionId,
                                    matchmakerId,
                                    modeConfig,
                                    modeRequests,
                                    modeMatches);
                            matchmakerChangeOfState.getCreatedMatches()
                                    .addAll(greedyMatchmakingResult.createdMatches());
                            matchmakerChangeOfState.getUpdatedMatches()
                                    .addAll(greedyMatchmakingResult.updatedMatches());
                            matchmakerChangeOfState.getDeletedRequests()
                                    .addAll(greedyMatchmakingResult.completedRequests());
                        } else {
                            log.warn("Unknown mode for matchmaking, mode={}", modeName);
                            matchmakerChangeOfState.getDeletedRequests().addAll(modeRequests);
                        }
                    });
                });
    }
}
