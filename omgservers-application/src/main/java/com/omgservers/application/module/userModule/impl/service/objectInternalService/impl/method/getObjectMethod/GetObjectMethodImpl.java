package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.getObjectMethod;

import com.omgservers.application.module.userModule.impl.operation.selectObjectOperation.SelectObjectOperation;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.GetObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.GetObjectInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetObjectMethodImpl implements GetObjectMethod {

    final CheckShardOperation checkShardOperation;
    final SelectObjectOperation selectObjectOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetObjectInternalResponse> getObject(final GetObjectInternalRequest request) {
        GetObjectInternalRequest.validate(request);

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
