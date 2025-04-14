package com.omgservers.service.shard.root.impl.service.rootService.impl.method.root;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.root.GetRootRequest;
import com.omgservers.schema.shard.root.root.GetRootResponse;
import com.omgservers.service.shard.root.impl.operation.root.SelectRootOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRootMethodImpl implements GetRootMethod {

    final SelectRootOperation selectRootOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRootResponse> execute(final ShardModel shardModel,
                                        final GetRootRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectRootOperation
                        .execute(sqlConnection, shardModel.shard(), id))
                .map(GetRootResponse::new);
    }
}
