package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.executeMatchmaker;

import com.omgservers.dto.matchmaker.ExecuteMatchmakerRequest;
import com.omgservers.dto.matchmaker.ExecuteMatchmakerResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakingResults.MatchmakingResultsModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.system.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking.DoGreedyMatchmakingOperation;
import com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId.SelectMatchesByMatchmakerIdOperation;
import com.omgservers.module.matchmaker.impl.operation.selectRequestsByMatchmakerId.SelectRequestsByMatchmakerIdOperation;
import com.omgservers.module.matchmaker.impl.operation.updateMatchConfig.UpdateMatchConfigOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
    final UpdateMatchConfigOperation updateMatchConfigOperation;
    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<ExecuteMatchmakerResponse> executeMatchmaker(ExecuteMatchmakerRequest request) {
        ExecuteMatchmakerRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                final var tenantId = matchmaker.getTenantId();
                                final var stageId = matchmaker.getStageId();
                                final var getCurrentVersionIdShardedRequest =
                                        new GetStageVersionIdRequest(tenantId, stageId);
                                return tenantModule.getVersionService()
                                        .getStageVersionId(getCurrentVersionIdShardedRequest)
                                        .map(GetStageVersionIdResponse::getVersionId)
                                        .flatMap(versionId -> getVersionStageConfig(tenantId, versionId)
                                                .flatMap(stageConfig -> executeMatchmaker(
                                                        shardModel.shard(),
                                                        tenantId,
                                                        stageId,
                                                        versionId,
                                                        matchmakerId,
                                                        stageConfig)
                                                        .map(ExecuteMatchmakerResponse::new)));
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(Long id) {
        final var request = new GetMatchmakerRequest(id);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<VersionConfigModel> getVersionStageConfig(final Long tenantId, final Long versionId) {
        final var request = new GetVersionConfigRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionConfig(request)
                .map(GetVersionConfigResponse::getVersionConfig);
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
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertCreatedMatches(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakingResults.getCreatedMatches())
                                .flatMap(voidItem -> updateUpdatedMatches(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        matchmakerId,
                                        matchmakingResults.getUpdatedMatches()))
                                .flatMap(voidItem -> upsertMatchedClients(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        matchmakerId,
                                        matchmakingResults.getMatchedClients()))
                                .flatMap(voidItem -> deleteCompletedRequests(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        matchmakerId,
                                        matchmakingResults.getCompletedRequests()))
                                .replaceWith(true))
                .map(ChangeContext::getResult);
    }

    Uni<Void> upsertCreatedMatches(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch ->
                        upsertMatchOperation.upsertMatch(changeContext, sqlConnection, shard, createdMatch))
                .collect().asList()
                .invoke(results -> log.info("Created matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, createdMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> updateUpdatedMatches(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchModel> updatedMatches) {
        return Multi.createFrom().iterable(updatedMatches)
                .onItem().transformToUniAndConcatenate(updatedMatch -> updateMatchConfigOperation
                        .updateMatch(
                                changeContext,
                                sqlConnection,
                                shard,
                                updatedMatch.getMatchmakerId(),
                                updatedMatch.getId(),
                                updatedMatch.getConfig()))
                .collect().asList()
                .invoke(results -> log.info("Updated matches were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, updatedMatches.size()))
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchedClients(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long matchmakerId,
                                   final List<MatchClientModel> matchedClients) {
        return Multi.createFrom().iterable(matchedClients)
                .onItem().transformToUniAndConcatenate(matchedClient -> upsertMatchClientOperation
                        .upsertMatchClient(changeContext, sqlConnection, shard, matchedClient))
                .collect().asList()
                .invoke(results -> log.info("Matched clients were synchronized, " +
                        "matchmakerId={}, count={}", matchmakerId, matchedClients.size()))
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long matchmakerId,
                                      final List<RequestModel> completedRequests) {
        return Multi.createFrom().iterable(completedRequests)
                .onItem().transformToUniAndMerge(completedRequest -> deleteRequestOperation.deleteRequest(
                        changeContext,
                        sqlConnection,
                        shard,
                        completedRequest.getMatchmakerId(),
                        completedRequest.getId()))
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