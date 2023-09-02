package com.omgservers.module.user.impl.service.tokenShardedService.impl.method.createToken;

import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.module.user.factory.TokenModelFactory;
import com.omgservers.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.module.user.impl.operation.encodeToken.EncodeTokenOperation;
import com.omgservers.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.module.user.impl.operation.upsertToken.UpsertTokenOperation;
import com.omgservers.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTokenMethodImpl implements CreateTokenMethod {

    final ValidateCredentialsOperation validateCredentialsOperation;
    final CreateUserTokenOperation createUserTokenOperation;
    final UpsertTokenOperation upsertTokenOperation;
    final EncodeTokenOperation encodeTokenOperation;
    final SelectUserOperation selectUserOperation;
    final CheckShardOperation checkShardOperation;
    final GenerateIdOperation generateIdOperation;
    final GetConfigOperation getConfigOperation;

    final TokenModelFactory tokenModelFactory;

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
                            .flatMap(user -> {
                                final var tokenContainer = createUserTokenOperation.createUserToken(user);
                                final var tokenModel = tokenModelFactory.create(tokenContainer);
                                return upsertTokenOperation.upsertToken(sqlConnection, shard, tokenModel)
                                        .replaceWith(tokenContainer);
                            }));
                })
                .map(tokenContainer -> new CreateTokenShardedResponse(tokenContainer.getTokenObject(),
                        tokenContainer.getRawToken(),
                        tokenContainer.getLifetime()));
    }
}
