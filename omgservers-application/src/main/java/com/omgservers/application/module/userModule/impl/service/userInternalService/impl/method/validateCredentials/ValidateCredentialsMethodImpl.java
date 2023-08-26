package com.omgservers.application.module.userModule.impl.service.userInternalService.impl.method.validateCredentials;

import com.omgservers.application.module.userModule.impl.operation.selectUserOperation.SelectUserOperation;
import com.omgservers.application.module.userModule.impl.operation.validateCredentialsOperation.ValidateCredentialsOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.userModule.ValidateCredentialsRoutedRequest;
import com.omgservers.dto.userModule.ValidateCredentialsInternalResponse;
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
    public Uni<ValidateCredentialsInternalResponse> validateCredentials(ValidateCredentialsRoutedRequest request) {
        ValidateCredentialsRoutedRequest.validate(request);

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
