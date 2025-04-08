package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeState;

import com.omgservers.schema.model.runtimeState.RuntimeStateDto;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.module.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtime.SelectRuntimeOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.SelectActiveRuntimeAssignmentsByRuntimeIdOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.SelectActiveRuntimeCommandsByRuntimeIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeStateMethodImpl implements GetRuntimeStateMethod {

    final SelectActiveRuntimeAssignmentsByRuntimeIdOperation selectActiveRuntimeAssignmentsByRuntimeIdOperation;
    final SelectActiveRuntimeCommandsByRuntimeIdOperation selectActiveRuntimeCommandsByRuntimeIdOperation;
    final SelectRuntimeOperation selectRuntimeOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeStateResponse> execute(final ShardModel shardModel,
                                                final GetRuntimeStateRequest request) {
        log.trace("{}", request);

        final var shard = shardModel.shard();
        final var runtimeId = request.getId();
        final var runtimeState = new RuntimeStateDto();
        return pgPool.withTransaction(sqlConnection ->
                        selectRuntimeOperation.execute(sqlConnection, shard, runtimeId)
                                .invoke(runtimeState::setRuntime)
                                .flatMap(deployment ->
                                        selectActiveRuntimeCommandsByRuntimeIdOperation
                                                .execute(sqlConnection, shard, runtimeId)
                                                .invoke(runtimeState::setRuntimeCommands))
                                .flatMap(runtimeCommands ->
                                        selectActiveRuntimeAssignmentsByRuntimeIdOperation
                                                .execute(sqlConnection, shard, runtimeId)
                                                .invoke(runtimeState::setRuntimeAssignments)))
                .replaceWith(runtimeState)
                .map(GetRuntimeStateResponse::new);
    }
}
