package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.root.SyncRootRequest;
import com.omgservers.schema.shard.root.root.SyncRootResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.root.impl.operation.root.UpsertRootOperation;
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

    @Override
    public Uni<SyncRootResponse> execute(final ShardModel shardModel,
                                         final SyncRootRequest request) {
        log.trace("{}", request);

        final var root = request.getRoot();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertRootOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                root))
                .map(ChangeContext::getResult)
                .map(SyncRootResponse::new);
    }
}
