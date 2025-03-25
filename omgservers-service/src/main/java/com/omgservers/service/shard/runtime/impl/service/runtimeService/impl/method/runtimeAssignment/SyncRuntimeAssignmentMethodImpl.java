package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.runtime.impl.operation.runtime.VerifyRuntimeExistsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.UpsertRuntimeAssignmentOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeAssignmentMethodImpl implements SyncRuntimeAssignmentMethod {

    final UpsertRuntimeAssignmentOperation upsertRuntimeAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyRuntimeExistsOperation verifyRuntimeExistsOperation;

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(final SyncRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeAssignment = request.getRuntimeAssignment();
        final var runtimeId = runtimeAssignment.getRuntimeId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyRuntimeExistsOperation
                                            .execute(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimeAssignmentOperation.execute(
                                                            changeContext,
                                                            sqlConnection,
                                                            shardModel.shard(),
                                                            runtimeAssignment);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncRuntimeAssignmentResponse::new);
    }
}
