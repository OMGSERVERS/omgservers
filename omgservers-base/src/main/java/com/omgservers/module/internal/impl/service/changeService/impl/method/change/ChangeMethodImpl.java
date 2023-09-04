package com.omgservers.module.internal.impl.service.changeService.impl.method.change;

import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.ChangeRequest;
import com.omgservers.ChangeResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeMethodImpl implements ChangeMethod {

    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final PgPool pgPool;

    @Override
    public Uni<ChangeResponse> change(ChangeRequest request) {
        ChangeRequest.validate(request);

        final var internalRequest = request.getRequest();
        final var changeFunction = request.getChangeFunction();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(internalRequest.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        changeFunction.apply(sqlConnection, shardModel))
                )
                .map(ChangeResponse::new);
    }
}
