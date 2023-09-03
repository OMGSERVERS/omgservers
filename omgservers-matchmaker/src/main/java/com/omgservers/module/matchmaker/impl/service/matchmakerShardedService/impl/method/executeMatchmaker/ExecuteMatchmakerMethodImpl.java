package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.executeMatchmaker;

import com.omgservers.ChangeContext;
import com.omgservers.Dispatcher;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
import com.omgservers.dto.tenant.GetVersionConfigShardedRequest;
import com.omgservers.dto.tenant.GetVersionConfigShardedResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakingResults.MatchmakingResultsModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId.SelectMatchesByMatchmakerIdOperation;
import com.omgservers.module.matchmaker.impl.operation.selectRequestsByMatchmakerId.SelectRequestsByMatchmakerIdOperation;
import com.omgservers.module.matchmaker.impl.operation.updateMatch.UpdateMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
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
    final TenantModule tenantModule;

    final SelectRequestsByMatchmakerIdOperation selectRequestsByMatchmakerIdOperation;
    final SelectMatchesByMatchmakerIdOperation selectMatchesByMatchmakerIdOperation;
    final DoGreedyMatchmakingOperation doGreedyMatchmakingOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final UpdateMatchOperation updateMatchOperation;
    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final Dispatcher dispatcher;

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
                                final var getCurrentVersionIdShardedRequest =
                                        new GetStageVersionIdShardedRequest(tenantId, stageId);
                                return tenantModule.getVersionShardedService()
                                        .getStageVersionId(getCurrentVersionIdShardedRequest)
                                        .map(GetStageVersionIdShardedResponse::getVersionId)
                                        .flatMap(versionId -> getVersionStageConfig(tenantId, versionId)
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

    Uni<VersionConfigModel> getVersionStageConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigShardedRequest(tenantId, versionId);
        return tenantModule.getVersionShardedService().getVersionConfig(request)
                .map(GetVersionConfigShardedResponse::getVersionConfig);
    }

    Uni<Boolean> executeMatchmaker(final int shard,
                                   final Long tenantId,
                                   final Long stageId,
                                   final Long versionId,
                                   final Long matchmakerId,
                                   final VersionConfigModel versionConfig) {
        return collectMatchmakerData(shard, matchmakerId)
                .flatMap(matchmakerData -> {
                    final var matchmakerRequests = matchmakerData.requests;
                    final var matchmakerMatches = matchmakerData.matches;
                    if (matchmakerRequests.isEmpty()) {
                        log.info("There aren't any requests for matchmaking, matchmakerId={}", matchmakerId);
                        return Uni.createFrom().item(false);
                    } else {
                        log.info("Execute matchmaker, matchmakerId={}, requests={}, matches={}",
                                matchmakerId, matchmakerRequests.size(), matchmakerMatches.size());
                        return doMatchmaking(
                                tenantId,
                                stageId,
                                versionId,
                                matchmakerId,
                                matchmakerRequests,
                                matchmakerMatches,
                                versionConfig)
                                .flatMap(matchmakingResults ->
                                        syncMatchmakingResults(matchmakerId, shard, matchmakingResults));
                    }
                });
    }

    Uni<MatchmakerData> collectMatchmakerData(final int shard, final Long matchmakerId) {
        return pgPool.withTransaction(sqlConnection -> selectRequestsByMatchmakerIdOperation
                .selectRequestsByMatchmakerId(sqlConnection, shard, matchmakerId)
                .flatMap(matchmakerRequests -> selectMatchesByMatchmakerIdOperation
                        .selectMatchesByMatchmakerId(sqlConnection, shard, matchmakerId)
                        .map(matchmakerMatches -> new MatchmakerData(matchmakerRequests, matchmakerMatches))));
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
                            matchmakingResults.getCompletedRequests().addAll(greedyMatchmakingResult.completedRequests());
                        } else {
                            log.info("Unknown mode for matchmaking within requests, mode={}", modeName);
                            matchmakingResults.getCompletedRequests().addAll(modeRequests);
                        }
                    });

                    return matchmakingResults;
                });
    }

    Uni<Boolean> syncMatchmakingResults(final Long matchmakerId,
                                        final int shard,
                                        final MatchmakingResultsModel matchmakingResults) {
        return changeWithContextOperation.changeWithContext((context, sqlConnection) ->
                upsertCreatedMatches(context, sqlConnection, shard, matchmakerId, matchmakingResults.getCreatedMatches())
                        .flatMap(voidItem -> updateUpdatedMatches(sqlConnection, shard, matchmakerId, matchmakingResults.getUpdatedMatches()))
                        .flatMap(voidItem -> upsertMatchedClients(sqlConnection, shard, matchmakerId, matchmakingResults.getMatchedClients()))
                        .flatMap(voidItem -> deleteCompletedRequests(sqlConnection, shard, matchmakerId, matchmakingResults.getCompletedRequests()))
                        .replaceWith(true));
    }

    Uni<Void> upsertCreatedMatches(final ChangeContext context,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch ->
                        upsertMatchOperation.upsertMatch(context, sqlConnection, shard, createdMatch))
                .collect().asList()
                .invoke(results -> log.info("Created matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, createdMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> updateUpdatedMatches(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchModel> updatedMatches) {
        return Multi.createFrom().iterable(updatedMatches)
                .onItem().transformToUniAndConcatenate(updatedMatch -> updateMatchOperation
                        .updateMatch(sqlConnection, shard, updatedMatch))
                .collect().asList()
                .invoke(results -> log.info("Updated matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, updatedMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchedClients(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchClientModel> matchedClients) {
        return Multi.createFrom().iterable(matchedClients)
                .onItem().transformToUniAndConcatenate(matchedClient -> upsertMatchClientOperation
                        .upsertMatchClient(sqlConnection, shard, matchedClient))
                .collect().asList()
                .invoke(results -> log.info("Matched clients were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, matchedClients.size()))
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long matchmakerId,
                                      final List<RequestModel> completedRequests) {
        return Multi.createFrom().iterable(completedRequests)
                .onItem().transformToUniAndMerge(completedRequest -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shard, completedRequest.getId()))
                .collect().asList()
                .invoke(results -> log.info("Completed requests were deleted, " +
                        "matchmakerId={}, count={}", matchmakerId, completedRequests.size()))
                .replaceWithVoid();
    }

    @AllArgsConstructor
    static class MatchmakerData {

        List<RequestModel> requests;
        List<MatchModel> matches;
    }
}