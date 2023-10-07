package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.syncMatchmakerState;

import com.omgservers.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.module.matchmaker.impl.operation.deleteMatch.DeleteMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchClient.DeleteMatchClientOperation;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchmakerCommand.DeleteMatchmakerCommandOperation;
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

import java.util.Collection;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerStateMethodImpl implements UpdateMatchmakerStateMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final UpdateMatchConfigOperation updateMatchConfigOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final DeleteMatchClientOperation deleteMatchClientOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final DeleteMatchOperation deleteMatchOperation;

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        final var matchmakerId = request.getMatchmakerId();
        final var changeOfState = request.getMatchmakerChangeOfState();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext(
                            (changeContext, sqlConnection) ->
                                    syncDeletedMatchmakerCommand(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerId,
                                            changeOfState.getDeletedMatchmakerCommands())
                                            .flatMap(voidItem -> syncCreatedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getCreatedMatches()))
                                            .flatMap(voidItem -> syncUpdatedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getUpdatedMatches()))
                                            .flatMap(voidItem -> syncDeletedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getDeletedMatches()))
                                            .flatMap(voidItem -> syncCreatedMatchClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getCreatedMatchClients()))
                                            .flatMap(voidItem -> syncDeletedMatchClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getDeletedMatchClients()))
                                            .flatMap(voidItem -> syncDeletedRequests(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    matchmakerId,
                                                    changeOfState.getDeletedRequests()))
                    );
                })
                .replaceWith(true)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> syncDeletedMatchmakerCommand(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long matchmakerId,
                                           final Collection<MatchmakerCommandModel> deletedMatchmakerCommands) {
        return Multi.createFrom().iterable(deletedMatchmakerCommands)
                .onItem().transformToUniAndConcatenate(deletedMatchmakerCommand ->
                        deleteMatchmakerCommandOperation.deleteMatchmakerCommand(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                deletedMatchmakerCommand.getId()))
                .collect().asList()
                .invoke(results -> {
                    if (deletedMatchmakerCommands.size() > 0) {
                        log.info("Deleted matchmaker commands were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, deletedMatchmakerCommands.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncCreatedMatches(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long matchmakerId,
                                 final Collection<MatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch ->
                        upsertMatchOperation.upsertMatch(changeContext, sqlConnection, shard, createdMatch))
                .collect().asList()
                .invoke(results -> {
                    if (createdMatches.size() > 0) {
                        log.info("Created matches were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, createdMatches.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncUpdatedMatches(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long matchmakerId,
                                 final Collection<MatchModel> updatedMatches) {
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
                .invoke(results -> {
                    if (updatedMatches.size() > 0) {
                        log.info("Updated matches were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, updatedMatches.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncDeletedMatches(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Long matchmakerId,
                                 final Collection<MatchModel> deletedMatches) {
        return Multi.createFrom().iterable(deletedMatches)
                .onItem().transformToUniAndConcatenate(deletedMatch -> deleteMatchOperation
                        .deleteMatch(
                                changeContext,
                                sqlConnection,
                                shard,
                                deletedMatch.getMatchmakerId(),
                                deletedMatch.getId()))
                .collect().asList()
                .invoke(results -> {
                    if (deletedMatches.size() > 0) {
                        log.info("Deleted matches were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, deletedMatches.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncCreatedMatchClients(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long matchmakerId,
                                      final Collection<MatchClientModel> createdMatchClients) {
        return Multi.createFrom().iterable(createdMatchClients)
                .onItem().transformToUniAndConcatenate(createdMatchedClient -> upsertMatchClientOperation
                        .upsertMatchClient(
                                changeContext,
                                sqlConnection,
                                shard,
                                createdMatchedClient))
                .collect().asList()
                .invoke(results -> {
                    if (createdMatchClients.size() > 0) {
                        log.info("Created match clients were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, createdMatchClients.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncDeletedMatchClients(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long matchmakerId,
                                      final Collection<MatchClientModel> deletedMatchClients) {
        return Multi.createFrom().iterable(deletedMatchClients)
                .onItem().transformToUniAndConcatenate(deletedMatchClient -> deleteMatchClientOperation
                        .deleteMatchClient(
                                changeContext,
                                sqlConnection,
                                shard,
                                deletedMatchClient.getMatchmakerId(),
                                deletedMatchClient.getId()))
                .collect().asList()
                .invoke(results -> {
                    if (deletedMatchClients.size() > 0) {
                        log.info("Deleted match clients were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, deletedMatchClients.size());
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> syncDeletedRequests(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final int shard,
                                  final Long matchmakerId,
                                  final Collection<RequestModel> deletedRequests) {
        return Multi.createFrom().iterable(deletedRequests)
                .onItem().transformToUniAndMerge(deletedRequest -> deleteRequestOperation.deleteRequest(
                        changeContext,
                        sqlConnection,
                        shard,
                        deletedRequest.getMatchmakerId(),
                        deletedRequest.getId()))
                .collect().asList()
                .invoke(results -> {
                    if (deletedRequests.size() > 0) {
                        log.info("Deleted requests were synchronized, " +
                                "matchmakerId={}, count={}", matchmakerId, deletedRequests.size());
                    }
                })
                .replaceWithVoid();
    }
}
