package com.omgservers.service.module.root.impl.service.rootService.impl.method.root.syncRoot;

import com.omgservers.schema.module.root.root.SyncRootRequest;
import com.omgservers.schema.module.root.root.SyncRootResponse;
import com.omgservers.service.module.root.impl.operation.root.upsertRoot.UpsertRootOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRootMethodImpl implements SyncRootMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertRootOperation upsertRootOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncRootResponse> syncRoot(final SyncRootRequest request) {
        log.debug("Sync root, request={}", request);

        final var root = request.getRoot();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertRootOperation.upsertRoot(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            root))
                            .map(ChangeContext::getResult);
                })
                .map(SyncRootResponse::new);
    }
}
