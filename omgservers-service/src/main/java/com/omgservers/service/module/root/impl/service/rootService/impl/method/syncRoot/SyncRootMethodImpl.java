package com.omgservers.service.module.root.impl.service.rootService.impl.method.syncRoot;

import com.omgservers.model.dto.root.SyncRootRequest;
import com.omgservers.model.dto.root.SyncRootResponse;
import com.omgservers.service.module.root.impl.operation.upsertRoot.UpsertRootOperation;
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
