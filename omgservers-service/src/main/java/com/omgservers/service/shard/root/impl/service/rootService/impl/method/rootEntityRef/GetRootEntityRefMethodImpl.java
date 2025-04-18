package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.SelectRootEntityRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRootEntityRefMethodImpl implements GetRootEntityRefMethod {

    final SelectRootEntityRefOperation selectRootEntityRefOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRootEntityRefResponse> execute(final ShardModel shardModel,
                                                 final GetRootEntityRefRequest request) {
        log.trace("{}", request);

        final var rootId = request.getRootId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectRootEntityRefOperation
                        .execute(sqlConnection, shardModel.slot(), rootId, id))
                .map(GetRootEntityRefResponse::new);
    }
}
