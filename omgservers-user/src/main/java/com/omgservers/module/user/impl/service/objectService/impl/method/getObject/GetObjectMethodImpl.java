package com.omgservers.module.user.impl.service.objectService.impl.method.getObject;

import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
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
    public Uni<GetObjectResponse> getObject(final GetObjectRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var playerId = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectObjectOperation
                            .selectObject(sqlConnection, shard.shard(), userId, playerId, name));
                })
                .map(GetObjectResponse::new);
    }
}
