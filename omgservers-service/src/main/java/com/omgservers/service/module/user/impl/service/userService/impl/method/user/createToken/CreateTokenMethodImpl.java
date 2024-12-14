package com.omgservers.service.module.user.impl.service.userService.impl.method.user.createToken;

import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.module.user.impl.operation.user.selectUser.SelectUserOperation;
import com.omgservers.service.module.user.impl.operation.user.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import com.omgservers.service.operation.issueJwtToken.IssueJwtTokenOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTokenMethodImpl implements CreateTokenMethod {

    final ValidateCredentialsOperation validateCredentialsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;
    final SelectUserOperation selectUserOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        log.trace("Requested, {}", request);

        final var userId = request.getUserId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var password = request.getPassword();
                    return changeWithContextOperation.<String>changeWithContext((changeContext, sqlConnection) ->
                                    selectUserOperation.selectUser(sqlConnection, shard, userId)
                                            .flatMap(user -> validateCredentialsOperation
                                                    .validateCredentials(user, password))
                                            .map(this::issueJwtToken))
                            .map(ChangeContext::getResult);
                })
                .map(CreateTokenResponse::new);
    }

    String issueJwtToken(final UserModel user) {
        final var userId = user.getId();
        final var groups = Set.of(user.getRole().getName());
        return issueJwtTokenOperation.issueUserJwtToken(userId, groups);
    }
}
