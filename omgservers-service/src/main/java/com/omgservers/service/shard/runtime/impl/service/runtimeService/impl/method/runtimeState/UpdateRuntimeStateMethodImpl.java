package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.UpdateRuntimeStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.DeleteRuntimeAssignmentOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.UpsertRuntimeAssignmentOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.DeleteRuntimeCommandOperation;
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
class UpdateRuntimeStateMethodImpl implements UpdateRuntimeStateMethod {

    final UpsertRuntimeAssignmentOperation upsertRuntimeAssignmentOperation;
    final DeleteRuntimeAssignmentOperation deleteRuntimeAssignmentOperation;
    final DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;

    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateRuntimeStateResponse> execute(final UpdateRuntimeStateRequest request) {
        log.trace("{}", request);

        final var runtimeChangeOfState = request.getRuntimeChangeOfState();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var runtimeId = request.getRuntimeId();
                    return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                            deleteRuntimeCommands(changeContext,
                                    sqlConnection,
                                    shard,
                                    runtimeId,
                                    runtimeChangeOfState.getRuntimeCommandsToDelete())
                                    .flatMap(voidItem -> upsertRuntimeAssignment(changeContext,
                                            sqlConnection,
                                            shard,
                                            runtimeChangeOfState.getRuntimeAssignmentToSync()))
                                    .flatMap(voidItem -> deleteRuntimeAssignments(changeContext,
                                            sqlConnection,
                                            shard,
                                            runtimeId,
                                            runtimeChangeOfState.getRuntimeAssignmentToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdateRuntimeStateResponse::new);
    }

    Uni<Void> deleteRuntimeCommands(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long runtimeId,
                                    final List<Long> runtimeCommands) {
        return Multi.createFrom().iterable(runtimeCommands)
                .onItem().transformToUniAndConcatenate(runtimeCommandId ->
                        deleteRuntimeCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                runtimeId,
                                runtimeCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertRuntimeAssignment(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final List<RuntimeAssignmentModel> runtimeAssignments) {
        return Multi.createFrom().iterable(runtimeAssignments)
                .onItem().transformToUniAndConcatenate(runtimeAssignment ->
                        upsertRuntimeAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                runtimeAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteRuntimeAssignments(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long runtimeId,
                                       final List<Long> runtimeAssignments) {
        return Multi.createFrom().iterable(runtimeAssignments)
                .onItem().transformToUniAndConcatenate(runtimeAssignmentId ->
                        deleteRuntimeAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                runtimeId,
                                runtimeAssignmentId))
                .collect().asList()
                .replaceWithVoid();
    }
}
