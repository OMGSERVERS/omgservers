package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.syncUserMethod;

import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.SyncUserInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.SyncUserInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncUserMethodImpl implements SyncUserMethod {

    final CheckShardOperation checkShardOperation;
    final UpsertUserOperation upsertUserOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncUserInternalResponse> syncUser(final SyncUserInternalRequest request) {
        SyncUserInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var user = request.getUser();
                    return pgPool.withTransaction(sqlConnection ->
                            upsertUserOperation.upsertUser(sqlConnection, shardModel.shard(), user));
                })
                .map(SyncUserInternalResponse::new);
    }
}
