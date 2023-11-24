package com.omgservers.service.module.user.impl.service.tokenService.impl.method.createToken;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.service.factory.TokenModelFactory;
import com.omgservers.service.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.service.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.service.module.user.impl.operation.upsertToken.UpsertTokenOperation;
import com.omgservers.service.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        log.debug("Create token, request={}", request);

        final var userId = request.getUserId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var password = request.getPassword();
                    return changeWithContextOperation.<UserTokenContainerModel>changeWithContext(
                                    (changeContext, sqlConnection) ->
                                            selectUserOperation.selectUser(sqlConnection, shard, userId, false)
                                                    .flatMap(user -> validateCredentialsOperation
                                                            .validateCredentials(user, password))
                                                    .flatMap(user -> {
                                                        final var tokenContainer = createUserTokenOperation
                                                                .createUserToken(user);
                                                        final var tokenModel = tokenModelFactory.create(tokenContainer);
                                                        return upsertTokenOperation.upsertToken(changeContext,
                                                                        sqlConnection,
                                                                        shard,
                                                                        tokenModel)
                                                                .replaceWith(tokenContainer);
                                                    }))
                            .map(ChangeContext::getResult);
                })
                .map(tokenContainer -> new CreateTokenResponse(tokenContainer.getTokenObject(),
                        tokenContainer.getRawToken(),
                        tokenContainer.getLifetime()));
    }
}
