package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.getObjectMethod;

import com.omgservers.application.module.userModule.impl.operation.selectObjectOperation.SelectObjectOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.userModule.GetObjectShardRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
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
    public Uni<GetObjectInternalResponse> getObject(final GetObjectShardRequest request) {
        GetObjectShardRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var player = request.getPlayerId();
                    final var name = request.getName();
                    return pgPool.withTransaction(sqlConnection -> selectObjectOperation
                            .selectObject(sqlConnection, shard.shard(), player, name));
                })
                .map(GetObjectInternalResponse::new);
    }
}
