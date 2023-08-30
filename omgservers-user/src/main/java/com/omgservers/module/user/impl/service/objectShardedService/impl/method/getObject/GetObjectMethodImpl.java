package com.omgservers.module.user.impl.service.objectShardedService.impl.method.getObject;

import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.module.user.impl.operation.selectObject.SelectObjectOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetObjectMethodImpl implements GetObjectMethod {

    final CheckShardOperation checkShardOperation;
    final SelectObjectOperation selectObjectOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetObjectShardedResponse> getObject(final GetObjectShardedRequest request) {
        GetObjectShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var player = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectObjectOperation
                            .selectObject(sqlConnection, shard.shard(), player, name));
                })
                .map(GetObjectShardedResponse::new);
    }
}