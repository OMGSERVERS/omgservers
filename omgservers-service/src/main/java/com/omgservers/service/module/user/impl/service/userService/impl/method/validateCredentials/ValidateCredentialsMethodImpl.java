package com.omgservers.service.module.user.impl.service.userService.impl.method.validateCredentials;

import com.omgservers.model.dto.user.ValidateCredentialsRequest;
import com.omgservers.model.dto.user.ValidateCredentialsResponse;
import com.omgservers.service.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.service.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateCredentialsMethodImpl implements ValidateCredentialsMethod {

    final ValidateCredentialsOperation validateCredentialsOperation;
    final CheckShardOperation checkShardOperation;
    final SelectUserOperation selectUserOperation;

    final PgPool pgPool;

    @Override
    public Uni<ValidateCredentialsResponse> validateCredentials(ValidateCredentialsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var password = request.getPassword();
                    return pgPool.withTransaction(sqlConnection -> selectUserOperation
                                    .selectUser(sqlConnection, shard.shard(), userId, false)
                                    .flatMap(userModel -> validateCredentialsOperation
                                            .validateCredentials(userModel, password)))
                            .map(ValidateCredentialsResponse::new);
                });
    }
}
