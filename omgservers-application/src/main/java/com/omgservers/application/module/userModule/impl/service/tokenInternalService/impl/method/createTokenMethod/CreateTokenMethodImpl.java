package com.omgservers.application.module.userModule.impl.service.tokenInternalService.impl.method.createTokenMethod;

import com.omgservers.application.module.userModule.impl.operation.insertTokenOperation.InsertTokenOperation;
import com.omgservers.application.module.userModule.impl.operation.selectUserOperation.SelectUserOperation;
import com.omgservers.application.module.userModule.impl.operation.validateCredentialsOperation.ValidateCredentialsOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.userModule.CreateTokenRoutedRequest;
import com.omgservers.dto.userModule.CreateTokenInternalResponse;
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
    public Uni<CreateTokenInternalResponse> createToken(final CreateTokenRoutedRequest request) {
        CreateTokenRoutedRequest.validate(request);

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
                .map(tokenContainer -> new CreateTokenInternalResponse(tokenContainer.getTokenObject(),
                        tokenContainer.getRawToken(),
                        tokenContainer.getLifetime()));
    }
}
