package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.user.CreateTokenRequest;
import com.omgservers.schema.shard.user.CreateTokenResponse;
import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.user.SelectUserOperation;
import com.omgservers.service.shard.user.impl.operation.user.ValidateCredentialsOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<CreateTokenResponse> createToken(final ShardModel shardModel,
                                                final CreateTokenRequest request) {
        log.debug("{}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();
        return changeWithContextOperation.<String>changeWithContext((changeContext, sqlConnection) ->
                        selectUserOperation.execute(sqlConnection, shardModel.slot(), userId)
                                .flatMap(user -> validateCredentialsOperation
                                        .execute(user, password))
                                .map(this::issueJwtToken))
                .map(ChangeContext::getResult)
                .map(CreateTokenResponse::new);
    }

    String issueJwtToken(final UserModel user) {
        final var userId = user.getId();
        final var groups = Set.of(user.getRole().getName());
        return issueJwtTokenOperation.issueUserJwtToken(userId, groups);
    }
}
