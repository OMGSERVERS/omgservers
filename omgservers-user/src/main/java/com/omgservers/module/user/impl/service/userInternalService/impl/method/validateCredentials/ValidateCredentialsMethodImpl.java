package com.omgservers.module.user.impl.service.userInternalService.impl.method.validateCredentials;

import com.omgservers.dto.user.ValidateCredentialsShardedResponse;
import com.omgservers.dto.user.ValidateCredentialsShardedRequest;
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
class ValidateCredentialsMethodImpl implements ValidateCredentialsMethod {

    final ValidateCredentialsOperation validateCredentialsOperation;
    final CheckShardOperation checkShardOperation;
    final SelectUserOperation selectUserOperation;

    final PgPool pgPool;

    @Override
    public Uni<ValidateCredentialsShardedResponse> validateCredentials(ValidateCredentialsShardedRequest request) {
        ValidateCredentialsShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var password = request.getPassword();
                    return pgPool.withTransaction(sqlConnection -> selectUserOperation
                                    .selectUser(sqlConnection, shard.shard(), userId)
                                    .flatMap(userModel -> validateCredentialsOperation
                                            .validateCredentials(userModel, password)))
                            .map(ValidateCredentialsShardedResponse::new);
                });
    }
}