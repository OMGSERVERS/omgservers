package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.dto.matchmaker.DeleteRequestShardedResponse;
import com.omgservers.dto.matchmaker.DeleteRequestShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchShardedResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.MatchmakerShardedService;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
import com.omgservers.dto.matchmaker.DoGreedyMatchmakingRequest;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DoGreedyMatchmakingMethodImpl implements DoGreedyMatchmakingMethod {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final MatchmakerShardedService matchmakerShardedService;

    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    @Override
    public Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingRequest request) {
        DoGreedyMatchmakingRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var requests = request.getRequests();
        final var matches = request.getMatches();
        final var stageConfig = request.getStageConfig();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> doMatchmaking(tenantId,
                        stageId,
                        versionId,
                        matchmakerId,
                        requests,
                        matches,
                        stageConfig))
                .flatMap(this::syncOverallResults);
    }

    OverallMatchmakingResults doMatchmaking(final Long tenantId,
                                            final Long stageId,
                                            final Long versionId,
                                            final Long matchmakerId,
                                            final List<RequestModel> requests,
                                            final List<MatchModel> matches,
                                            final VersionStageConfigModel stageConfig) {
        // TODO: this code is not a thread-safe, do we need to use lock for matchmakerId here to prevent concurrent execution

        final var groupedRequests = requests.stream()
                .collect(Collectors.groupingBy(request -> request.getConfig().getMode()));

        final var groupedMatches = matches.stream()
                .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

        final var overallResults = new OverallMatchmakingResults();

        groupedRequests.forEach((modeName, activeRequests) -> {
            final var launchedMatches = groupedMatches.getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = stageConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName)).findFirst();
            if (modeConfigOptional.isPresent()) {
                final var modeConfig = modeConfigOptional.get();
                final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(tenantId,
                        stageId,
                        versionId,
                        matchmakerId,
                        modeConfig,
                        activeRequests,
                        launchedMatches);
                overallResults.getMatchedRequests().addAll(greedyMatchmakingResult.matchedRequests());
                overallResults.getFailedRequests().addAll(greedyMatchmakingResult.failedRequests());
                overallResults.getPreparedMatches().addAll(greedyMatchmakingResult.preparedMatches());
            } else {
                overallResults.getFailedRequests().addAll(activeRequests);
            }
        });

        return overallResults;
    }

    Uni<Void> syncOverallResults(final OverallMatchmakingResults results) {
        return syncPreparedMatches(results.getPreparedMatches())
                .flatMap(voidItem -> {
                    final var matchedRequests = results.getMatchedRequests();
                    final var failedRequests = results.getFailedRequests();
                    final var completedRequests = new ArrayList<RequestModel>();
                    completedRequests.addAll(matchedRequests);
                    completedRequests.addAll(failedRequests);
                    log.info("There are completed requests, matched={}, failed={}, total={}",
                            matchedRequests.size(), failedRequests.size(), completedRequests.size());
                    return deleteCompletedRequests(completedRequests);
                });
    }

    Uni<Void> syncPreparedMatches(final List<MatchModel> preparedMatches) {
        // TODO: use batching???
        return Multi.createFrom().iterable(preparedMatches)
                .onItem().transformToUniAndMerge(match -> {
                    final var request = new SyncMatchShardedRequest(match);
                    return matchmakerShardedService.syncMatch(request);
                })
                .collect().asList()
                .invoke(results -> {
                    final var createdCount = results.stream().filter(SyncMatchShardedResponse::getCreated).count();
                    final var updatedCount = results.size() - createdCount;
                    log.info("Prepared matches were synchronized, created={}, updated={}", createdCount, updatedCount);
                })
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final List<RequestModel> completedRequests) {
        // TODO: use batching???
        return Multi.createFrom().iterable(completedRequests)
                .onItem().transformToUniAndMerge(request -> {
                    final var matchmaker = request.getMatchmakerId();
                    final var id = request.getId();
                    final var deleteRequestInternalRequest = new DeleteRequestShardedRequest(matchmaker, id);
                    return matchmakerShardedService.deleteRequest(deleteRequestInternalRequest);
                })
                .collect().asList()
                .invoke(results -> {
                    final var deletedCount = results.stream().filter(DeleteRequestShardedResponse::getDeleted).count();
                    log.info("Completed requests were deleted, count={}", deletedCount);
                })
                .replaceWithVoid();
    }

    @Data
    static class OverallMatchmakingResults {
        final List<RequestModel> matchedRequests;
        final List<RequestModel> failedRequests;
        final List<MatchModel> preparedMatches;

        public OverallMatchmakingResults() {
            matchedRequests = new ArrayList<>();
            failedRequests = new ArrayList<>();
            preparedMatches = new ArrayList<>();
        }
    }
}
