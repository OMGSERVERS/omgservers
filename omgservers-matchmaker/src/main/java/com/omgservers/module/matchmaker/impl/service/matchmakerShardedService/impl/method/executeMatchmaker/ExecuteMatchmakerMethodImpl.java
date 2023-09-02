package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.executeMatchmaker;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.tenant.GetStageVersionRequest;
import com.omgservers.dto.tenant.GetStageVersionResponse;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakingResults.MatchmakingResultsModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.updateMatch.UpdateMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteMatchmakerMethodImpl implements ExecuteMatchmakerMethod {

    final MatchmakerModule matchmakerModule;
    final VersionModule versionModule;
    final TenantModule tenantModule;

    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final UpdateMatchOperation updateMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final PgPool pgPool;

    @Override
    public Uni<ExecuteMatchmakerShardedResponse> executeMatchmaker(ExecuteMatchmakerShardedRequest request) {
        ExecuteMatchmakerShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                final var tenantId = matchmaker.getTenantId();
                                final var stageId = matchmaker.getStageId();
                                final var getStageVersionRequest = new GetStageVersionRequest(tenantId, stageId);
                                return tenantModule.getStageService().getStageVersion(getStageVersionRequest)
                                        .map(GetStageVersionResponse::getVersionId)
                                        .flatMap(versionId -> getVersionStageConfig(versionId)
                                                .flatMap(stageConfig -> executeMatchmaker(
                                                        shardModel.shard(),
                                                        tenantId,
                                                        stageId,
                                                        versionId,
                                                        matchmakerId,
                                                        stageConfig)
                                                        .map(ExecuteMatchmakerShardedResponse::new)));
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(Long id) {
        final var request = new GetMatchmakerShardedRequest(id);
        return matchmakerModule.getMatchmakerShardedService().getMatchmaker(request)
                .map(GetMatchmakerShardedResponse::getMatchmaker);
    }

    Uni<VersionStageConfigModel> getVersionStageConfig(final Long versionId) {
        final var request = new GetStageConfigShardedRequest(versionId);
        return versionModule.getVersionShardedService().getStageConfig(request)
                .map(GetStageConfigShardedResponse::getStageConfig);
    }

    Uni<Boolean> executeMatchmaker(final int shard,
                                   final Long tenantId,
                                   final Long stageId,
                                   final Long versionId,
                                   final Long matchmakerId,
                                   final VersionStageConfigModel stageConfig) {
        final var matchmakerRequests = matchmakerInMemoryCache.getRequests(matchmakerId);
        final var matchmakerMatches = matchmakerInMemoryCache.getMatches(matchmakerId);
        if (matchmakerRequests.isEmpty()) {
            log.info("There aren't any requests for matchmaking, matchmakerId={}", matchmakerId);
            return Uni.createFrom().item(false);
        } else {
            // TODO: detect type of matchmaking
            return doMatchmaking(
                    tenantId,
                    stageId,
                    versionId,
                    matchmakerId,
                    matchmakerRequests,
                    matchmakerMatches,
                    stageConfig)
                    .flatMap(matchmakingResults -> syncMatchmakingResults(matchmakerId, shard, matchmakingResults))
                    .replaceWith(true);
        }
    }

    Uni<MatchmakingResultsModel> doMatchmaking(final Long tenantId,
                                               final Long stageId,
                                               final Long versionId,
                                               final Long matchmakerId,
                                               final List<RequestModel> matchmakerRequests,
                                               final List<MatchModel> matchmakerMatches,
                                               final VersionStageConfigModel stageConfig) {

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

                        final var modeConfigOptional = stageConfig.getModes().stream()
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
                            matchmakingResults.getCompletedRequests().addAll(greedyMatchmakingResult.completedRequests());
                        } else {
                            log.info("Unknown mode for matchmaking within requests, mode={}", modeName);
                            matchmakingResults.getCompletedRequests().addAll(modeRequests);
                        }
                    });

                    return matchmakingResults;
                });
    }

    Uni<Void> syncMatchmakingResults(final Long matchmakerId,
                                     final int shard,
                                     final MatchmakingResultsModel matchmakingResults) {
        return pgPool.withTransaction(sqlConnection ->
                upsertCreatedMatches(
                        matchmakerId,
                        sqlConnection,
                        shard,
                        matchmakingResults.getCreatedMatches())
                        .flatMap(voidItem -> updateUpdatedMatches(matchmakerId, sqlConnection, shard,
                                matchmakingResults.getUpdatedMatches()))
                        .flatMap(voidItem -> upsertMatchedClients(matchmakerId, sqlConnection, shard,
                                matchmakingResults.getMatchedClients()))
                        .flatMap(voidItem -> deleteCompletedRequests(matchmakerId, sqlConnection, shard,
                                matchmakingResults.getCompletedRequests())));
    }

    Uni<Void> upsertCreatedMatches(final Long matchmakerId,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final List<MatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch -> upsertMatchOperation
                        .upsertMatch(sqlConnection, shard, createdMatch))
                .collect().asList()
                .invoke(results -> log.info("Created matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, createdMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> updateUpdatedMatches(final Long matchmakerId,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final List<MatchModel> updatedMatches) {
        return Multi.createFrom().iterable(updatedMatches)
                .onItem().transformToUniAndConcatenate(updatedMatch -> updateMatchOperation
                        .updateMatch(sqlConnection, shard, updatedMatch))
                .collect().asList()
                .invoke(results -> log.info("Updated matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, updatedMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchedClients(final Long matchmakerId,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final List<MatchClientModel> matchedClients) {
        return Multi.createFrom().iterable(matchedClients)
                .onItem().transformToUniAndConcatenate(matchedClient -> upsertMatchClientOperation
                        .upsertMatchClient(sqlConnection, shard, matchedClient))
                .collect().asList()
                .invoke(results -> log.info("Matched clients were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, matchedClients.size()))
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final Long matchmakerId,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final List<RequestModel> completedRequests) {
        return Multi.createFrom().iterable(completedRequests)
                .onItem().transformToUniAndMerge(completedRequest -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shard, completedRequest.getId()))
                .collect().asList()
                .invoke(results -> log.info("Completed requests were deleted, " +
                        "matchmakerId={}, count={}", matchmakerId, completedRequests.size()))
                .replaceWithVoid();
    }
}