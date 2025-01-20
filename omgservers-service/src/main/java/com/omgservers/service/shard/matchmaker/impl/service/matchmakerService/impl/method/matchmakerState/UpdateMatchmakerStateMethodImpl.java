package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.UpdateMatchmakerStateResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment.DeleteMatchmakerAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerAssignment.UpsertMatchmakerAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.DeleteMatchmakerCommandOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.DeleteMatchmakerMatchOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.UpdateMatchmakerMatchStatusOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.UpsertMatchmakerMatchOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.UpsertMatchmakerMatchAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.DeleteMatchmakerRequestOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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

    final UpsertMatchmakerMatchAssignmentOperation upsertMatchmakerMatchAssignmentOperation;
    final DeleteMatchmakerMatchAssignmentOperation deleteMatchmakerMatchAssignmentOperation;
    final UpdateMatchmakerMatchStatusOperation updateMatchmakerMatchStatusOperation;
    final UpsertMatchmakerAssignmentOperation upsertMatchmakerAssignmentOperation;
    final DeleteMatchmakerAssignmentOperation deleteMatchmakerAssignmentOperation;
    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;
    final DeleteMatchmakerMatchOperation deleteMatchmakerMatchOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(final UpdateMatchmakerStateRequest request) {
        log.trace("{}", request);

        final var matchmakerChangeOfState = request.getMatchmakerChangeOfState();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                            upsertMatchmakerAssignments(changeContext,
                                    sqlConnection,
                                    shard,
                                    matchmakerChangeOfState.getAssignmentsToSync())
                                    .flatMap(voidItem -> deleteMatchmakerAssignments(changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getAssignmentsToDelete()))
                                    .flatMap(voidItem -> deleteCommands(changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getCommandsToDelete()))
                                    .flatMap(voidItem -> deleteRequests(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getRequestsToDelete()))
                                    .flatMap(voidItem -> upsertMatchmakerMatches(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchesToSync()))
                                    .flatMap(voidItem -> updateMatchmakerMatcheStatus(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchesToUpdateStatus()))
                                    .flatMap(voidItem -> deleteMatchmakerMatches(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchesToDelete()))
                                    .flatMap(voidItem -> upsertMatchmakerMatchAssignments(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchAssignmentsToSync()))
                                    .flatMap(voidItem -> deleteMatchmakerMatchAssignments(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchAssignmentsToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> upsertMatchmakerAssignments(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Collection<MatchmakerAssignmentModel> matchmakerAssignments) {
        return Multi.createFrom().iterable(matchmakerAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerAssignment ->
                        upsertMatchmakerAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                matchmakerAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerAssignments(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Collection<MatchmakerAssignmentModel> matchmakerAssignments) {
        return Multi.createFrom().iterable(matchmakerAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerAssignment ->
                        deleteMatchmakerAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerAssignment.getMatchmakerId(),
                                matchmakerAssignment.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteCommands(final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final int shard,
                             final Collection<MatchmakerCommandModel> commands) {
        return Multi.createFrom().iterable(commands)
                .onItem().transformToUniAndConcatenate(command -> deleteMatchmakerCommandOperation
                        .execute(
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
                        .execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                request.getMatchmakerId(),
                                request.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchmakerMatches(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match -> upsertMatchmakerMatchOperation
                        .execute(changeContext, sqlConnection, shard, match))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateMatchmakerMatcheStatus(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match ->
                        updateMatchmakerMatchStatusOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                match.getMatchmakerId(),
                                match.getId(),
                                match.getStatus())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerMatches(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Collection<MatchmakerMatchModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem()
                .transformToUniAndConcatenate(match -> deleteMatchmakerMatchOperation.execute(
                        changeContext,
                        sqlConnection,
                        shard,
                        match.getMatchmakerId(),
                        match.getId()))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchmakerMatchAssignments(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Collection<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments) {
        return Multi.createFrom().iterable(matchmakerMatchAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignment ->
                        upsertMatchmakerMatchAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                matchmakerMatchAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerMatchAssignments(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Collection<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments) {
        return Multi.createFrom().iterable(matchmakerMatchAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignment ->
                        deleteMatchmakerMatchAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerMatchAssignment.getMatchmakerId(),
                                matchmakerMatchAssignment.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
