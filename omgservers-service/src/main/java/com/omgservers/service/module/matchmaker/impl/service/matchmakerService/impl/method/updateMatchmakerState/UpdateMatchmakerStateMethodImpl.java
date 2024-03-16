package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerState;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatch.DeleteMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatchClient.DeleteMatchmakerMatchClientOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerCommand.DeleteMatchmakerCommandOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerRequest.DeleteMatchmakerRequestOperation;
import com.omgservers.service.module.matchmaker.impl.operation.updateMatchmakerMatchStoppedFlag.UpdateMatchmakerMatchStoppedFlagOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatch.UpsertMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchClient.UpsertMatchmakerMatchClientOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final UpdateMatchmakerMatchStoppedFlagOperation updateMatchmakerMatchStoppedFlagOperation;
    final UpsertMatchmakerMatchClientOperation upsertMatchmakerMatchClientOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteMatchmakerMatchClientOperation deleteMatchmakerMatchClientOperation;
    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;
    final DeleteMatchmakerMatchOperation deleteMatchmakerMatchOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerStateResponse> updateMatchmakerState(final UpdateMatchmakerStateRequest request) {
        log.debug("Update matchmaker state, request={}", request);

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
                                      final Collection<MatchmakerRequestModel> deletedRequests) {
        return Multi.createFrom().iterable(deletedRequests)
                .onItem().transformToUniAndConcatenate(deletedRequest -> deleteMatchmakerRequestOperation.deleteMatchmakerRequest(
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
                                 final Collection<MatchmakerMatchModel> createdMatches) {
        return Multi.createFrom().iterable(createdMatches)
                .onItem().transformToUniAndConcatenate(createdMatch ->
                        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(changeContext, sqlConnection, shard, createdMatch))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateStoppedMatches(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Collection<MatchmakerMatchModel> updatedMatches) {
        return Multi.createFrom().iterable(updatedMatches)
                .onItem().transformToUniAndConcatenate(updatedMatch ->
                        updateMatchmakerMatchStoppedFlagOperation.updateMatchmakerMatchStoppedFlag(changeContext,
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
                                 final Collection<MatchmakerMatchModel> endedMatches) {
        return Multi.createFrom().iterable(endedMatches)
                .onItem().transformToUniAndConcatenate(endedMatch -> deleteMatchmakerMatchOperation.deleteMatchmakerMatch(
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
                                      final Collection<MatchmakerMatchClientModel> createdMatchClients) {
        return Multi.createFrom().iterable(createdMatchClients)
                .onItem().transformToUniAndConcatenate(createdMatchClient ->
                        upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(changeContext,
                                sqlConnection,
                                shard,
                                createdMatchClient))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteOrphanedMatchClients(final ChangeContext<?> changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final Collection<MatchmakerMatchClientModel> orphanedMatchClients) {
        return Multi.createFrom().iterable(orphanedMatchClients)
                .onItem()
                .transformToUniAndConcatenate(orphanedMatchClient -> deleteMatchmakerMatchClientOperation.deleteMatchmakerMatchClient(
                        changeContext,
                        sqlConnection,
                        shard,
                        orphanedMatchClient.getMatchmakerId(),
                        orphanedMatchClient.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
