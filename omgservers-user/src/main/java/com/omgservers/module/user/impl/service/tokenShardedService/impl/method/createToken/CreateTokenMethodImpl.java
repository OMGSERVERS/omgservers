package com.omgservers.module.user.impl.service.tokenShardedService.impl.method.createToken;

import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.module.user.impl.operation.insertToken.InsertTokenOperation;
import com.omgservers.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTokenMethodImpl implements CreateTokenMethod {

    final CheckShardOperation checkShardOperation;
    final SelectUserOperation selectUserOperation;
    final ValidateCredentialsOperation validateCredentialsOperation;
    final InsertTokenOperation insertTokenOperation;
    final PgPool pgPool;

    @Override
    public Uni<CreateTokenShardedResponse> createToken(final CreateTokenShardedRequest request) {
        CreateTokenShardedRequest.validate(request);

        final var userUuid = request.getUserId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var password = request.getPassword();
                    return pgPool.withTransaction(sqlConnection -> selectUserOperation
                            .selectUser(sqlConnection, shard, userUuid)
                            .flatMap(user -> validateCredentialsOperation.validateCredentials(user, password))
                            .flatMap(user -> insertTokenOperation.insertToken(sqlConnection, shard, user)));
                })
                .map(tokenContainer -> new CreateTokenShardedResponse(tokenContainer.getTokenObject(),
                        tokenContainer.getRawToken(),
                        tokenContainer.getLifetime()));
    }
}
