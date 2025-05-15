package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtime.SelectRuntimeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeMethodImpl implements GetRuntimeMethod {

    final SelectRuntimeOperation selectRuntimeOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeResponse> execute(final ShardModel shardModel,
                                           final GetRuntimeRequest request) {
        log.debug("{}", request);
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectRuntimeOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetRuntimeResponse::new);
    }
}
