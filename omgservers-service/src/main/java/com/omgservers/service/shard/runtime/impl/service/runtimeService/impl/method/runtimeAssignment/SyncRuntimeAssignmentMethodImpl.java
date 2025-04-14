package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.VerifyRuntimeExistsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.UpsertRuntimeAssignmentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeAssignmentMethodImpl implements SyncRuntimeAssignmentMethod {

    final UpsertRuntimeAssignmentOperation upsertRuntimeAssignmentOperation;
    final VerifyRuntimeExistsOperation verifyRuntimeExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(final ShardModel shardModel,
                                                      final SyncRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        final var runtimeAssignment = request.getRuntimeAssignment();
        final var runtimeId = runtimeAssignment.getRuntimeId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyRuntimeExistsOperation
                                .execute(sqlConnection, shardModel.shard(), runtimeId)
                                .flatMap(exists -> {
                                    if (exists) {
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
                .map(ChangeContext::getResult)
                .map(SyncRuntimeAssignmentResponse::new);
    }
}
