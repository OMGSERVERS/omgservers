package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.shard.user.impl.operation.user.SelectUserOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetUserResponse> getUser(final ShardModel shardModel,
                                        final GetUserRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectUserOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetUserResponse::new);
    }
}
