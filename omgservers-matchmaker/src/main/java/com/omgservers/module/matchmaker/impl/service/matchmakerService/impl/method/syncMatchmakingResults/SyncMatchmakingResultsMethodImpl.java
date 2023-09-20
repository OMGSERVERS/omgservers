package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakingResults;

import com.omgservers.dto.matchmaker.SyncMatchmakingResultsRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakingResultsResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.updateMatchConfig.UpdateMatchConfigOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakingResultsMethodImpl implements SyncMatchmakingResultsMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final UpdateMatchConfigOperation updateMatchConfigOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;

    @Override
    public Uni<SyncMatchmakingResultsResponse> syncMatchmakingResults(final SyncMatchmakingResultsRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var matchmakingResults = request.getMatchmakingResultsModel();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext(
                            (changeContext, sqlConnection) ->
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
                    );
                })
                .replaceWith(true)
                .map(SyncMatchmakingResultsResponse::new);
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
}
