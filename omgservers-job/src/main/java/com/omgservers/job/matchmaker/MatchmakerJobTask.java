package com.omgservers.job.matchmaker;

import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsResponse;
import com.omgservers.dto.matchmaker.ViewMatchesRequest;
import com.omgservers.dto.matchmaker.ViewMatchesResponse;
import com.omgservers.dto.matchmaker.ViewRequestsRequest;
import com.omgservers.dto.matchmaker.ViewRequestsResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.job.matchmaker.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.model.job.JobTypeEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakingResults.MatchmakingResultsModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerJobTask implements JobTask {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;

    @Override
    public JobTypeEnum getJobType() {
        return JobTypeEnum.MATCHMAKER;
    }

    @Override
    public Uni<Boolean> executeTask(final Long shardKey, final Long entityId) {
        final var matchmakerId = entityId;
        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var tenantId = matchmaker.getTenantId();
                    final var stageId = matchmaker.getStageId();
                    return getStageVersionId(tenantId, stageId)
                            .flatMap(versionId -> getVersionConfig(tenantId, versionId)
                                    .flatMap(versionConfig -> executeMatchmaker(
                                            matchmakerId,
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

    Uni<Boolean> executeMatchmaker(final Long matchmakerId,
                                   final Long tenantId,
                                   final Long stageId,
                                   final Long versionId,
                                   final VersionConfigModel versionConfig) {
        return viewRequests(matchmakerId)
                .flatMap(requests -> viewMatches(matchmakerId)
                        .flatMap(matches -> {
                            if (requests.isEmpty()) {
                                log.info("There aren't any requests for matchmaking, matchmakerId={}", matchmakerId);
                                return Uni.createFrom().item(false);
                            } else {
                                log.info("Execute matchmaker, matchmakerId={}, requests={}, matches={}",
                                        matchmakerId, requests.size(), matches.size());
                                return doMatchmaking(
                                        tenantId,
                                        stageId,
                                        versionId,
                                        matchmakerId,
                                        requests,
                                        matches,
                                        versionConfig)
                                        .flatMap(matchmakingResults ->
                                                syncMatchmakingResults(matchmakerId, matchmakingResults));
                            }
                        }))
                .replaceWith(true);
    }

    Uni<List<RequestModel>> viewRequests(final Long matchmakerId) {
        final var request = new ViewRequestsRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewRequests(request)
                .map(ViewRequestsResponse::getRequests);
    }

    Uni<List<MatchModel>> viewMatches(final Long matchmakerId) {
        final var request = new ViewMatchesRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().viewMatches(request)
                .map(ViewMatchesResponse::getMatches);
    }

    Uni<MatchmakingResultsModel> doMatchmaking(final Long tenantId,
                                               final Long stageId,
                                               final Long versionId,
                                               final Long matchmakerId,
                                               final List<RequestModel> matchmakerRequests,
                                               final List<MatchModel> matchmakerMatches,
                                               final VersionConfigModel versionConfig) {

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> {
                    final var groupedRequests = matchmakerRequests.stream()
                            .collect(Collectors.groupingBy(RequestModel::getMode));

                    final var groupedMatches = matchmakerMatches.stream()
                            .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

                    final var matchmakingResults = new MatchmakingResultsModel();

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
                            matchmakingResults.getCreatedMatches().addAll(greedyMatchmakingResult.createdMatches());
                            matchmakingResults.getUpdatedMatches().addAll(greedyMatchmakingResult.updatedMatches());
                            matchmakingResults.getMatchedClients().addAll(greedyMatchmakingResult.matchedClients());
                            matchmakingResults.getCompletedRequests()
                                    .addAll(greedyMatchmakingResult.completedRequests());
                        } else {
                            log.warn("Unknown mode for matchmaking, mode={}", modeName);
                            matchmakingResults.getCompletedRequests().addAll(modeRequests);
                        }
                    });

                    return matchmakingResults;
                });
    }

    Uni<Boolean> syncMatchmakingResults(final Long matchmakerId,
                                        final MatchmakingResultsModel matchmakingResults) {
        final var request = new SyncMatchmakingResultsRequest(matchmakerId, matchmakingResults);
        return matchmakerModule.getMatchmakerService().syncMatchmakingResults(request)
                .map(SyncMatchmakingResultsResponse::getCompleted);
    }
}
