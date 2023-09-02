package com.omgservers.module.matchmaker.impl.service.matchmakingService.impl.method.doGreedyMatchmakingMethod;

import com.omgservers.dto.matchmaker.DoMatchmakingRequest;
import com.omgservers.dto.matchmaker.DoMatchmakingResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.MatchmakerShardedService;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
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

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final PgPool pgPool;

    @Override
    public Uni<DoMatchmakingResponse> doGreedyMatchmaking(DoMatchmakingRequest request) {
        DoMatchmakingRequest.validate(request);

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
                        stageConfig));
    }

    DoMatchmakingResponse doMatchmaking(final Long tenantId,
                                        final Long stageId,
                                        final Long versionId,
                                        final Long matchmakerId,
                                        final List<RequestModel> matchmakerRequests,
                                        final List<MatchModel> matchmakerMatches,
                                        final VersionStageConfigModel stageConfig) {
        // TODO: this code is not a thread-safe, do we need to use lock for matchmakerId here to prevent concurrent execution

        final var groupedRequests = matchmakerRequests.stream()
                .collect(Collectors.groupingBy(RequestModel::getMode));

        final var groupedMatches = matchmakerMatches.stream()
                .collect(Collectors.groupingBy(match -> match.getConfig().getModeConfig().getName()));

        final var response = new DoMatchmakingResponse();

        groupedRequests.forEach((modeName, activeRequests) -> {
            final var launchedMatches = groupedMatches.getOrDefault(modeName, new ArrayList<>());

            final var modeConfigOptional = stageConfig.getModes().stream()
                    .filter(mode -> mode.getName().equals(modeName)).findFirst();
            if (modeConfigOptional.isPresent()) {
                final var modeConfig = modeConfigOptional.get();
                // TODO: detect matchmaking method
                final var greedyMatchmakingResult = doGreedyMatchmakingOperation.doGreedyMatchmaking(
                        tenantId,
                        stageId,
                        versionId,
                        matchmakerId,
                        modeConfig,
                        activeRequests,
                        launchedMatches);
                response.getCreatedMatches().addAll(greedyMatchmakingResult.createdMatches());
                response.getUpdatedMatches().addAll(greedyMatchmakingResult.updatedMatches());
                response.getMatchedClients().addAll(greedyMatchmakingResult.matchedClients());
                response.getFailedRequests().addAll(greedyMatchmakingResult.failedRequests());
            } else {
                log.info("Unknown mode for matchmaking within requests, mode={}", modeName);
                response.getFailedRequests().addAll(activeRequests);
            }
        });

        return response;
    }

//    Uni<Void> syncOverallResults(final OverallMatchmakingResults results) {
//        return pgPool.withTransaction(sqlConnection -> {
//
//        });
//
//
//        return syncCreatedMatches(results.getCreatedMatches())
//                .flatMap(voidItem -> syncMatchedClient(results.getMatchedClients()))
//                .flatMap(voidItem -> {
//                    final var matchedRequestIds = results.getMatchedClients().stream()
//                            .map(MatchClientModel::getRequestId)
//                            .toList();
//                    final var failedRequestIds = results.getFailedRequests().stream()
//                            .map(RequestModel::getId)
//                            .toList();
//                    final var completedRequestIds = new ArrayList<Long>();
//                    completedRequestIds.addAll(matchedRequestIds);
//                    completedRequestIds.addAll(failedRequestIds);
//                    log.info("There are completed requests, matched={}, failed={}, total={}",
//                            matchedRequestIds.size(), failedRequestIds.size(), completedRequestIds.size());
//                    return deleteCompletedRequests(results.getMatchmakerId(), completedRequestIds);
//                });
//    }
//
//    Uni<Void> upsertCreatedMatches(SqlConnection sqlConnection, final List<MatchModel> createdMatches) {
//        return Multi.createFrom().iterable(createdMatches)
//                .onItem().transformToUniAndConcatenate(createdMatch -> upsertMatchOperation
//                        .upsertMatch(sqlConnection, ))
//                .collect().asList()
//                .invoke(results -> log.info("Created matches were synchronized, count={}", createdMatches.size()))
//                .replaceWithVoid();
//    }
//
//    Uni<Void> syncCreatedMatches(final List<MatchModel> createdMatches) {
//        // TODO: use batching???
//        return Multi.createFrom().iterable(createdMatches)
//                .onItem().transformToUniAndMerge(createdMatch -> {
//                    final var request = new SyncMatchShardedRequest(createdMatch);
//                    return matchmakerShardedService.syncMatch(request);
//                })
//                .collect().asList()
//                .invoke(results -> log.info("Created matches were synchronized, count={}", createdMatches.size()))
//                .replaceWithVoid();
//    }
//
//    Uni<Void> syncMatchedClient(final List<MatchClientModel> matchedClients) {
//        // TODO: use batching???
//        return Multi.createFrom().iterable(matchedClients)
//                .onItem().transformToUniAndMerge(matchedClient -> {
//                    final var request = new SyncMatchClientShardedRequest(matchedClient);
//                    return matchmakerShardedService.syncMatchClient(request);
//                })
//                .collect().asList()
//                .invoke(results -> log.info("Matched clients were synchronized, count={}", matchedClients.size()))
//                .replaceWithVoid();
//    }
//
//    Uni<Void> deleteCompletedRequests(final Long matchmakerId, final List<Long> completedRequestIds) {
//        // TODO: use batching???
//        return Multi.createFrom().iterable(completedRequestIds)
//                .onItem().transformToUniAndMerge(requestId -> {
//                    final var deleteRequestInternalRequest = new DeleteRequestShardedRequest(matchmakerId, requestId);
//                    return matchmakerShardedService.deleteRequest(deleteRequestInternalRequest);
//                })
//                .collect().asList()
//                .invoke(results -> {
//                    final var deletedCount = results.stream().filter(DeleteRequestShardedResponse::getDeleted).count();
//                    log.info("Completed requests were deleted, count={}", deletedCount);
//                })
//                .replaceWithVoid();
//    }
}
