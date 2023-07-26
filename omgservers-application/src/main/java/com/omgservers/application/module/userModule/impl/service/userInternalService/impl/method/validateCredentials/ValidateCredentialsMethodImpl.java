package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.validateCredentials;

import com.omgservers.application.module.userModule.impl.operation.selectUserOperation.SelectUserOperation;
import com.omgservers.application.module.userModule.impl.operation.validateCredentialsOperation.ValidateCredentialsOperation;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.ValidateCredentialsInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.response.ValidateCredentialsInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateCredentialsMethodImpl implements ValidateCredentialsMethod {

    final ValidateCredentialsOperation validateCredentialsOperation;
    final CheckShardOperation checkShardOperation;
    final SelectUserOperation selectUserOperation;

    final PgPool pgPool;

    @Override
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsInternalRequest request) {
        ValidateCredentialsInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var userId = request.getUserId();
                    final var password = request.getPassword();
                    return pgPool.withTransaction(sqlConnection -> selectUserOperation
                                    .selectUser(sqlConnection, shard.shard(), userId)
                                    .flatMap(userModel -> validateCredentialsOperation
                                            .validateCredentials(userModel, password)))
                            .map(ValidateCredentialsInternalResponse::new);
                });
    }
}
