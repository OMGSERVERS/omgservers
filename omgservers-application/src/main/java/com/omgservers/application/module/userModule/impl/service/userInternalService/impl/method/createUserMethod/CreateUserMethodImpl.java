package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.createUserMethod;

import com.omgservers.application.module.userModule.impl.operation.insertUserOperation.InsertUserOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateUserMethodImpl implements CreateUserMethod {

    final CheckShardOperation checkShardOperation;
    final InsertUserOperation insertUserOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> createUser(final CreateUserInternalRequest request) {
        CreateUserInternalRequest.validate(request);

        final var user = request.getUser();
        final var shardKey = user.getUuid().toString();
        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        insertUserOperation.insertUser(sqlConnection, shardModel.shard(), user)));
    }
}
