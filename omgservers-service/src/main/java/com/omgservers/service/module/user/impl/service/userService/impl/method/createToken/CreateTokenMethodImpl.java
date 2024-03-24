package com.omgservers.service.module.user.impl.service.userService.impl.method.createToken;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.user.CreateTokenResponse;
import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.service.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.jwt.build.Jwt;
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

    private final String ISSUER = "https://omgservers.com";

    final ValidateCredentialsOperation validateCredentialsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final SelectUserOperation selectUserOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<CreateTokenResponse> createToken(final CreateTokenRequest request) {
        log.debug("Create token, request={}", request);

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
        String jwtToken = Jwt.issuer(ISSUER)
                .upn(user.getId().toString())
                .groups(Set.of(user.getRole().getName()))
                .sign();
        return jwtToken;
    }
}
