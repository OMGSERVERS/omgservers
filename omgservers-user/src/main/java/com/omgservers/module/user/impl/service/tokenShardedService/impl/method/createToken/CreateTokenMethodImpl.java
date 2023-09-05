package com.omgservers.module.user.impl.service.tokenShardedService.impl.method.createToken;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.CreateTokenShardedRequest;
import com.omgservers.dto.user.CreateTokenShardedResponse;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.module.user.factory.TokenModelFactory;
import com.omgservers.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.module.user.impl.operation.upsertToken.UpsertTokenOperation;
import com.omgservers.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
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

    final ValidateCredentialsOperation validateCredentialsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CreateUserTokenOperation createUserTokenOperation;
    final UpsertTokenOperation upsertTokenOperation;
    final SelectUserOperation selectUserOperation;
    final CheckShardOperation checkShardOperation;

    final TokenModelFactory tokenModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<CreateTokenShardedResponse> createToken(final CreateTokenShardedRequest request) {
        CreateTokenShardedRequest.validate(request);

        final var userId = request.getUserId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var password = request.getPassword();
                    return changeWithContextOperation.<UserTokenContainerModel>changeWithContext((changeContext, sqlConnection) ->
                                    selectUserOperation.selectUser(sqlConnection, shard, userId)
                                            .flatMap(user -> validateCredentialsOperation.validateCredentials(user, password))
                                            .flatMap(user -> {
                                                final var tokenContainer = createUserTokenOperation.createUserToken(user);
                                                final var tokenModel = tokenModelFactory.create(tokenContainer);
                                                return upsertTokenOperation
                                                        .upsertToken(changeContext, sqlConnection, shard, tokenModel)
                                                        .replaceWith(tokenContainer);
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(tokenContainer -> new CreateTokenShardedResponse(tokenContainer.getTokenObject(),
                        tokenContainer.getRawToken(),
                        tokenContainer.getLifetime()));
    }
}
