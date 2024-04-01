package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.deletePoolServerRef;

import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerRef.deletePoolServerRef.DeletePoolServerRefOperation;
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
class DeletePoolServerRefMethodImpl implements DeletePoolServerRefMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePoolServerRefOperation deletePoolServerRefOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolServerRefResponse> deletePoolServerRef(final DeletePoolServerRefRequest request) {
        log.debug("Delete pool server ref, request={}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolServerRefOperation
                                        .deletePoolServerRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolServerRefResponse::new);
    }
}
