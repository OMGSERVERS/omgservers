package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerMatchResourceToUpdateStatusDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<UpdateMatchmakerStateResponse> execute(final ShardModel shardModel,
                                                      final UpdateMatchmakerStateRequest request) {
        log.trace("{}", request);

        final var slot = shardModel.slot();
        final var matchmakerChangeOfState = request.getMatchmakerChangeOfState();
        final var matchmakerId = request.getMatchmakerId();
        return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                        deleteMatchmakerCommands(
                                changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerChangeOfState.getMatchmakerCommandsToDelete())
                                .flatMap(voidItem -> deleteMatchmakerRequests(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerId,
                                        matchmakerChangeOfState.getMatchmakerRequestsToDelete()))
                                .flatMap(voiItem -> upsertMatchmakerMatches(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerChangeOfState.getMatchmakerMatchResourcesToSync()))
                                .flatMap(voidItem -> updateMatchmakerMatchResourcesStatus(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerId,
                                        matchmakerChangeOfState.getMatchmakerMatchResourcesToUpdateStatus()))
                                .flatMap(voidItem -> deleteMatchmakerMatchResources(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerId,
                                        matchmakerChangeOfState.getMatchmakerMatchResourcesToDelete()))
                                .flatMap(voidItem -> upsertMatchmakerMatchAssignments(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerChangeOfState.getMatchmakerMatchAssignmentsToSync()))
                                .flatMap(voidItem -> deleteMatchmakerMatchAssignments(
                                        changeContext,
                                        sqlConnection,
                                        slot,
                                        matchmakerId,
                                        matchmakerChangeOfState.getMatchmakerMatchAssignmentsToDelete()))
                )
                .replaceWith(Boolean.TRUE)
                .map(UpdateMatchmakerStateResponse::new);
    }

    Uni<Void> deleteMatchmakerCommands(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int slot,
                                       final Long matchmakerId,
                                       final List<Long> matchmakerCommands) {
        return Multi.createFrom().iterable(matchmakerCommands)
                .onItem().transformToUniAndConcatenate(matchmakerCommandId ->
                        deleteMatchmakerCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerRequests(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int slot,
                                       final Long matchmakerId,
                                       final List<Long> matchmakerRequests) {
        return Multi.createFrom().iterable(matchmakerRequests)
                .onItem().transformToUniAndConcatenate(matchmakerRequestId ->
                        deleteMatchmakerRequestOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerRequestId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchmakerMatches(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int slot,
                                      final Collection<MatchmakerMatchResourceModel> matches) {
        return Multi.createFrom().iterable(matches)
                .onItem().transformToUniAndConcatenate(match -> upsertMatchmakerMatchResourceOperation
                        .execute(changeContext, sqlConnection, slot, match))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateMatchmakerMatchResourcesStatus(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int slot,
                                                   final Long matchmakerId,
                                                   final List<MatchmakerMatchResourceToUpdateStatusDto> matchmakerMatchResources) {
        return Multi.createFrom().iterable(matchmakerMatchResources)
                .onItem().transformToUniAndConcatenate(matchmakerMatchResource ->
                        updateMatchmakerMatchResourceStatusOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerMatchResource.id(),
                                matchmakerMatchResource.status())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerMatchResources(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int slot,
                                             final Long matchmakerId,
                                             final List<Long> matchmakerMatchResources) {
        return Multi.createFrom().iterable(matchmakerMatchResources)
                .onItem().transformToUniAndConcatenate(matchmakerMatchResourceId ->
                        deleteMatchmakerMatchResourceOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerMatchResourceId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertMatchmakerMatchAssignments(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int slot,
                                               final Collection<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments) {
        return Multi.createFrom().iterable(matchmakerMatchAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignment ->
                        upsertMatchmakerMatchAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                slot,
                                matchmakerMatchAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteMatchmakerMatchAssignments(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int slot,
                                               final Long matchmakerId,
                                               final List<Long> matchmakerMatchAssignments) {
        return Multi.createFrom().iterable(matchmakerMatchAssignments)
                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignmentId ->
                        deleteMatchmakerMatchAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                slot,
                                matchmakerId,
                                matchmakerMatchAssignmentId))
                .collect().asList()
                .replaceWithVoid();
    }
}
