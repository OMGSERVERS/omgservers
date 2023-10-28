package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerState;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.module.matchmaker.impl.operation.deleteMatch.DeleteMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchClient.DeleteMatchClientOperation;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchmakerCommand.DeleteMatchmakerCommandOperation;
import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.module.matchmaker.impl.operation.updateMatchStoppedFlag.UpdateMatchStoppedFlagOperation;
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

    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final UpsertMatchClientOperation upsertMatchClientOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchClientOperation deleteMatchClientOperation;
    final DeleteRequestOperation deleteRequestOperation;
    final UpsertMatchOperation upsertMatchOperation;
    final DeleteMatchOperation deleteMatchOperation;
    final UpdateMatchStoppedFlagOperation updateMatchStoppedFlagOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        final var changeOfState = request.getChangeOfState();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext(
                            (changeContext, sqlConnection) ->
                                    deleteCompletedRequests(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            changeOfState.getCompletedRequests())
                                            .flatMap(voidItem -> deleteCompletedMatchmakerCommands(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getCompletedMatchmakerCommands()))
                                            .flatMap(voidItem -> syncCreatedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getCreatedMatches()))
                                            .flatMap(voidItem -> updateStoppedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getStoppedMatches()))
                                            .flatMap(voidItem -> deleteEndedMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getEndedMatches()))
                                            .flatMap(voidItem -> syncCreatedMatchClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getCreatedMatchClients()))
                                            .flatMap(voidItem -> deleteOrphanedMatchClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getOrphanedMatchClients()))
                    );
                })
                .replaceWith(true)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> deleteCompletedMatchmakerCommands(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final Collection<MatchmakerCommandModel> completedMatchmakerCommands) {
        return Multi.createFrom().iterable(completedMatchmakerCommands)
                .onItem().transformToUniAndConcatenate(completedMatchmakerCommand -> deleteMatchmakerCommandOperation
                        .deleteMatchmakerCommand(
                                changeContext,
                                sqlConnection,
                                shard,
                                completedMatchmakerCommand.getMatchmakerId(),
                                completedMatchmakerCommand.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteCompletedRequests(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Collection<RequestModel> deletedRequests) {
        return Multi.createFrom().iterable(deletedRequests)
                .onItem().transformToUniAndConcatenate(deletedRequest -> deleteRequestOperation.deleteRequest(
                        changeContext,
                        sqlConnection,
                        shard,
                        deletedRequest.getMatchmakerId(),
                        deletedRequest.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncCreatedMatches(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Collection<MatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch ->
                        upsertMatchOperation.upsertMatch(changeContext, sqlConnection, shard, createdMatch))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateStoppedMatches(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Collection<MatchModel> updatedMatches) {
        return Multi.createFrom().iterable(updatedMatches)
                .onItem().transformToUniAndConcatenate(updatedMatch ->
                        updateMatchStoppedFlagOperation.updateMatch(changeContext,
                                sqlConnection,
                                shard,
                                updatedMatch.getMatchmakerId(),
                                updatedMatch.getId(),
                                updatedMatch.getStopped())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteEndedMatches(final ChangeContext<?> changeContext,
                                 final SqlConnection sqlConnection,
                                 final int shard,
                                 final Collection<MatchModel> endedMatches) {
        return Multi.createFrom().iterable(endedMatches)
                .onItem().transformToUniAndConcatenate(endedMatch -> deleteMatchOperation.deleteMatch(
                        changeContext,
                        sqlConnection,
                        shard,
                        endedMatch.getMatchmakerId(),
                        endedMatch.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncCreatedMatchClients(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Collection<MatchClientModel> createdMatchClients) {
        return Multi.createFrom().iterable(createdMatchClients)
                .onItem().transformToUniAndConcatenate(createdMatchClient ->
                        upsertMatchClientOperation.upsertMatchClient(changeContext,
                                sqlConnection,
                                shard,
                                createdMatchClient))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteOrphanedMatchClients(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Collection<MatchClientModel> orphanedMatchClients) {
        return Multi.createFrom().iterable(orphanedMatchClients)
                .onItem()
                .transformToUniAndConcatenate(orphanedMatchClient -> deleteMatchClientOperation.deleteMatchClient(
                        changeContext,
                        sqlConnection,
                        shard,
                        orphanedMatchClient.getMatchmakerId(),
                        orphanedMatchClient.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
