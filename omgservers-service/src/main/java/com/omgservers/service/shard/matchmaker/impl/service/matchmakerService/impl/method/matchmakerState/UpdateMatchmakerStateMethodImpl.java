package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerMatchResourceToUpdateStatusDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerCommand.DeleteMatchmakerCommandOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.UpsertMatchmakerMatchAssignmentOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.DeleteMatchmakerMatchResourceOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.UpdateMatchmakerMatchResourceStatusOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.UpsertMatchmakerMatchResourceOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.DeleteMatchmakerRequestOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerStateMethodImpl implements UpdateMatchmakerStateMethod {

    final UpdateMatchmakerMatchResourceStatusOperation updateMatchmakerMatchResourceStatusOperation;
    final UpsertMatchmakerMatchAssignmentOperation upsertMatchmakerMatchAssignmentOperation;
    final DeleteMatchmakerMatchAssignmentOperation deleteMatchmakerMatchAssignmentOperation;
    final UpsertMatchmakerMatchResourceOperation upsertMatchmakerMatchResourceOperation;
    final DeleteMatchmakerMatchResourceOperation deleteMatchmakerMatchResourceOperation;
    final DeleteMatchmakerRequestOperation deleteMatchmakerRequestOperation;
    final DeleteMatchmakerCommandOperation deleteMatchmakerCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(final UpdateMatchmakerStateRequest request) {
        log.trace("{}", request);

        final var matchmakerChangeOfState = request.getMatchmakerChangeOfState();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var matchmakerId = request.getMatchmakerId();
                    return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                            deleteMatchmakerCommands(
                                    changeContext,
                                    sqlConnection,
                                    shard,
                                    matchmakerId,
                                    matchmakerChangeOfState.getMatchmakerCommandsToDelete())
                                    .flatMap(voidItem -> deleteMatchmakerRequests(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerId,
                                            matchmakerChangeOfState.getMatchmakerRequestsToDelete()))
                                    .flatMap(voiItem -> upsertMatchmakerMatches(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchmakerMatchResourcesToSync()))
                                    .flatMap(voidItem -> updateMatchmakerMatchResourcesStatus(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerId,
                                            matchmakerChangeOfState.getMatchmakerMatchResourcesToUpdateStatus()))
                                    .flatMap(voidItem -> deleteMatchmakerMatchResources(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerId,
                                            matchmakerChangeOfState.getMatchmakerMatchResourcesToDelete()))
                                    .flatMap(voidItem -> upsertMatchmakerMatchAssignments(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerChangeOfState.getMatchmakerMatchAssignmentsToSync()))
                                    .flatMap(voidItem -> deleteMatchmakerMatchAssignments(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            matchmakerId,
                                            matchmakerChangeOfState.getMatchmakerMatchAssignmentsToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> deleteMatchmakerCommands(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long matchmakerId,
                                       final List<Long> matchmakerCommands) {
        return Multi.createFrom().iterable(matchmakerCommands)
                .onItem().transformToUniAndConcatenate(matchmakerCommandId ->
                        deleteMatchmakerCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakerCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerRequests(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long matchmakerId,
                                       final List<Long> matchmakerRequests) {
        return Multi.createFrom().iterable(matchmakerRequests)
                .onItem().transformToUniAndConcatenate(matchmakerRequestId ->
                        deleteMatchmakerRequestOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakerRequestId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchmakerMatches(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Collection<MatchmakerMatchResourceModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match -> upsertMatchmakerMatchResourceOperation
                        .execute(changeContext, sqlConnection, shard, match))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateMatchmakerMatchResourcesStatus(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long matchmakerId,
                                                   final List<MatchmakerMatchResourceToUpdateStatusDto> matchmakerMatchResources) {
        return Multi.createFrom().iterable(matchmakerMatchResources)
                .onItem().transformToUniAndConcatenate(matchmakerMatchResource ->
                        updateMatchmakerMatchResourceStatusOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakerMatchResource.id(),
                                matchmakerMatchResource.status())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerMatchResources(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long matchmakerId,
                                             final List<Long> matchmakerMatchResources) {
        return Multi.createFrom().iterable(matchmakerMatchResources)
                .onItem().transformToUniAndConcatenate(matchmakerMatchResourceId ->
                        deleteMatchmakerMatchResourceOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakerMatchResourceId))
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
                                               final Long matchmakerId,
                                               final List<Long> matchmakerMatchAssignments) {
        return Multi.createFrom().iterable(matchmakerMatchAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignmentId ->
                        deleteMatchmakerMatchAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                matchmakerId,
                                matchmakerMatchAssignmentId))
                .collect().asList()
                .replaceWithVoid();
    }
}
