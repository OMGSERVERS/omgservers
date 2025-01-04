package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.UpdateRuntimeAssignmentLastActivityOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateRuntimeAssignmentLastActivityMethodImpl implements UpdateRuntimeAssignmentLastActivityMethod {

    final UpdateRuntimeAssignmentLastActivityOperation updateRuntimeAssignmentLastActivityOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(
            final UpdateRuntimeAssignmentLastActivityRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var clientId = request.getClientId();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updateRuntimeAssignmentLastActivityOperation
                                            .execute(changeContext,
                                                    sqlConnection,
                                                    shardModel.shard(),
                                                    runtimeId,
                                                    clientId))
                            .map(ChangeContext::getResult);
                })
                .map(UpdateRuntimeAssignmentLastActivityResponse::new);
    }
}
