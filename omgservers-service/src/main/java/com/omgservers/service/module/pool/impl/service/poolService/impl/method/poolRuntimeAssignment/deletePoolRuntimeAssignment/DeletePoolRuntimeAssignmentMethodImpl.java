package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.deletePoolRuntimeAssignment;

import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.deletePoolRuntimeAssignment.DeletePoolRuntimeAssignmentOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeletePoolRuntimeAssignmentMethodImpl implements DeletePoolRuntimeAssignmentMethod {

    final DeletePoolRuntimeAssignmentOperation deletePoolRuntimeAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(
            final DeletePoolRuntimeAssignmentRequest request) {
        log.debug("Delete pool runtime assignment, request={}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolRuntimeAssignmentOperation
                                        .deletePoolRuntimeAssignment(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolRuntimeAssignmentResponse::new);
    }
}
