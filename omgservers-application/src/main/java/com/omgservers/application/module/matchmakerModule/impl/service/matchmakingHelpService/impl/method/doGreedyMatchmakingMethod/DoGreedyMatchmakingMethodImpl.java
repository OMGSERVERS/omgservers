package com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation.DoGreedyMatchmakingOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation.UpsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncMatchInternalResponse;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakingHelpService.request.DoGreedyMatchmakingHelpRequest;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DoGreedyMatchmakingMethodImpl implements DoGreedyMatchmakingMethod {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final MatchmakerInternalService matchmakerInternalService;

    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    @Override
    public Uni<Void> doGreedyMatchmaking(DoGreedyMatchmakingHelpRequest request) {
        DoGreedyMatchmakingHelpRequest.validate(request);

        final var tenant = request.getTenant();
        final var stage = request.getStage();
        final var version = request.getVersion();
        final var matchmaker = request.getMatchmaker();
        final var requests = request.getRequests();
        final var matches = request.getMatches();
        final var stageConfig = request.getStageConfig();

        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(voidItem -> doMatchmaking(tenant,
                        stage,
                        version,
                        matchmaker,
                        requests,
                        matches,
                        stageConfig))
                .flatMap(this::syncOverallResults);
    }

    OverallMatchmakingResults doMatchmaking(final UUID tenant,
                                            final UUID stage,
                                            final UUID version,
                                            final UUID matchmaker,
                                            final List<RequestModel> matchmakerRequests,
                                            final List<MatchModel> matchmakerMatches,
                                            final VersionStageConfigModel stageConfig) {
        // TODO: this code is not a thread-safe, do we need to use lock for matchmaker here to prevent concurrent execution

        final var groupedRequests = matchmakerRequests.stream()
                .collect(Collectors.groupingBy(request -> request.getConfig().getMode()));

        final var groupedMatches = matchmakerMatches.stream()
                .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

        final var overallResults = new OverallMatchmakingResults();

        groupedRequests.forEach((modeName, activeRequests) -> {
            final var launchedMatches = groupedMatches.getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = stageConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName)).findFirst();
            if (modeConfigOptional.isPresent()) {
                final var modeConfig = modeConfigOptional.get();
                final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(tenant,
                        stage,
                        version,
                        matchmaker,
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
                    final var request = new SyncMatchInternalRequest(match);
                    return matchmakerInternalService.syncMatch(request);
                })
                .collect().asList()
                .invoke(results -> {
                    final var createdCount = results.stream().filter(SyncMatchInternalResponse::getCreated).count();
                    final var updatedCount = results.size() - createdCount;
                    log.info("Prepared matches were synchronized, created={}, updated={}", createdCount, updatedCount);
                })
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final List<RequestModel> completedRequests) {
        // TODO: use batching???
        return Multi.createFrom().iterable(completedRequests)
                .onItem().transformToUniAndMerge(request -> {
                    final var matchmaker = request.getMatchmaker();
                    final var uuid = request.getUuid();
                    final var deleteRequestInternalRequest = new DeleteRequestInternalRequest(matchmaker, uuid);
                    return matchmakerInternalService.deleteRequest(deleteRequestInternalRequest);
                })
                .collect().asList()
                .invoke(results -> {
                    final var deletedCount = results.stream().filter(DeleteRequestInternalResponse::getDeleted).count();
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
