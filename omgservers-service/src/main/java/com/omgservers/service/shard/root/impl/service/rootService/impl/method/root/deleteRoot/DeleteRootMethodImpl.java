package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root.deleteRoot;

import com.omgservers.schema.module.root.root.DeleteRootRequest;
import com.omgservers.schema.module.root.root.DeleteRootResponse;
import com.omgservers.service.shard.root.impl.operation.root.deleteRoot.DeleteRootOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRootMethodImpl implements DeleteRootMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRootOperation deleteRootOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRootResponse> deleteRoot(final DeleteRootRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteRootOperation.deleteRoot(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteRootResponse::new);
    }
}
