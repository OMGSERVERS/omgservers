package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.DeleteRootEntityRefOperation;
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

    @Override
    public Uni<DeleteRootEntityRefResponse> execute(final ShardModel shardModel,
                                                    final DeleteRootEntityRefRequest request) {
        log.trace("{}", request);

        final var rootId = request.getRootId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteRootEntityRefOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        rootId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteRootEntityRefResponse::new);
    }
}
