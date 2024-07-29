package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.deleteRootEntityRef;

import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.service.module.root.impl.operation.rootEntityRef.deleteRootEntityRef.DeleteRootEntityRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRootEntityRefMethodImpl implements DeleteRootEntityRefMethod {

    final DeleteRootEntityRefOperation deleteRootEntityRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRootEntityRefResponse> deleteRootEntityRef(
            final DeleteRootEntityRefRequest request) {
        log.debug("Delete root entity ref request, request={}", request);

        final var rootId = request.getRootId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteRootEntityRefOperation
                                        .deleteRootEntityRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                rootId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteRootEntityRefResponse::new);
    }
}
