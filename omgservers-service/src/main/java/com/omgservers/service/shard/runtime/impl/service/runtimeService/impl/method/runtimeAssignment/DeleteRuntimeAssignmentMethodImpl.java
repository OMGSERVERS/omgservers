package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.DeleteRuntimeAssignmentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeAssignmentMethodImpl implements DeleteRuntimeAssignmentMethod {

    final DeleteRuntimeAssignmentOperation deleteRuntimeAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> execute(final ShardModel shardModel,
                                                        final DeleteRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                runtimeId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteRuntimeAssignmentResponse::new);
    }
}
