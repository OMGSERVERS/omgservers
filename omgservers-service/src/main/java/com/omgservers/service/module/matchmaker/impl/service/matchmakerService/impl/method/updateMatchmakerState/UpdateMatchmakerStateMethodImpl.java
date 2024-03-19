package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.updateMatchmakerState;

import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerCommand.DeleteMatchmakerCommandOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatch.DeleteMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatchClient.DeleteMatchmakerMatchClientOperation;
import com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerRequest.DeleteMatchmakerRequestOperation;
import com.omgservers.service.module.matchmaker.impl.operation.updateMatchmakerMatchStatus.UpdateMatchmakerMatchStatusOperation;
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

    final UpdateMatchmakerMatchStatusOperation updateMatchmakerMatchStatusOperation;
    final UpsertMatchmakerMatchClientOperation upsertMatchmakerMatchClientOperation;
    final DeleteMatchmakerMatchClientOperation deleteMatchmakerMatchClientOperation;
    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;
    final DeleteMatchmakerMatchOperation deleteMatchmakerMatchOperation;
    final ChangeWithContextOperation changeWithContextOperation;
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
                                    deleteRequests(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            changeOfState.getRequestsToDelete())
                                            .flatMap(voidItem -> deleteCommands(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getCommandsToDelete()))
                                            .flatMap(voidItem -> syncMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getMatchesToSync()))
                                            .flatMap(voidItem -> updateMatchesStatus(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getMatchesToUpdateStatus()))
                                            .flatMap(voidItem -> deleteMatches(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getMatchesToDelete()))
                                            .flatMap(voidItem -> syncClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getClientsToSync()))
                                            .flatMap(voidItem -> deleteClients(
                                                    changeContext,
                                                    sqlConnection,
                                                    shard,
                                                    changeOfState.getClientsToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> deleteCommands(final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final int shard,
                             final Collection<MatchmakerCommandModel> commands) {
        return Multi.createFrom().iterable(commands)
                .onItem().transformToUniAndConcatenate(command -> deleteMatchmakerCommandOperation
                        .deleteMatchmakerCommand(
                                changeContext,
                                sqlConnection,
                                shard,
                                command.getMatchmakerId(),
                                command.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteRequests(final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final int shard,
                             final Collection<MatchmakerRequestModel> requests) {
        return Multi.createFrom().iterable(requests)
                .onItem().transformToUniAndConcatenate(request -> deleteMatchmakerRequestOperation
                        .deleteMatchmakerRequest(
                                changeContext,
                                sqlConnection,
                                shard,
                                request.getMatchmakerId(),
                                request.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncMatches(final ChangeContext<?> changeContext,
                          final SqlConnection sqlConnection,
                          final int shard,
                          final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match -> upsertMatchmakerMatchOperation
                        .upsertMatchmakerMatch(changeContext, sqlConnection, shard, match))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateMatchesStatus(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final int shard,
                                  final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match ->
                        updateMatchmakerMatchStatusOperation.updateMatchmakerMatchStatus(changeContext,
                                sqlConnection,
                                shard,
                                match.getMatchmakerId(),
                                match.getId(),
                                match.getStatus())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatches(final ChangeContext<?> changeContext,
                            final SqlConnection sqlConnection,
                            final int shard,
                            final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem()
                .transformToUniAndConcatenate(match -> deleteMatchmakerMatchOperation.deleteMatchmakerMatch(
                        changeContext,
                        sqlConnection,
                        shard,
                        match.getMatchmakerId(),
                        match.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> syncClients(final ChangeContext<?> changeContext,
                          final SqlConnection sqlConnection,
                          final int shard,
                          final Collection<MatchmakerMatchClientModel> clients) {
        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndConcatenate(client -> upsertMatchmakerMatchClientOperation
                        .upsertMatchmakerMatchClient(changeContext,
                                sqlConnection,
                                shard,
                                client))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteClients(final ChangeContext<?> changeContext,
                            final SqlConnection sqlConnection,
                            final int shard,
                            final Collection<MatchmakerMatchClientModel> clients) {
        return Multi.createFrom().iterable(clients)
                .onItem()
                .transformToUniAndConcatenate(client -> deleteMatchmakerMatchClientOperation
                        .deleteMatchmakerMatchClient(
                                changeContext,
                                sqlConnection,
                                shard,
                                client.getMatchmakerId(),
                                client.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
