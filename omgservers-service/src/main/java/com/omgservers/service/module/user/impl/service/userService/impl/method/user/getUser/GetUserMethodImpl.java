package com.omgservers.service.module.user.impl.service.userService.impl.method.user.getUser;

import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.service.module.user.impl.operation.user.selectUser.SelectUserOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetUserMethodImpl implements GetUserMethod {

    final SelectUserOperation selectUserOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetUserResponse> getUser(final GetUserRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectUserOperation
                            .selectUser(sqlConnection, shard.shard(), id));
                })
                .map(GetUserResponse::new);
    }
}
