package com.omgservers.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.module.system.impl.operation.selectServiceAccount.SelectServiceAccountOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetServiceAccountMethodImpl implements GetServiceAccountMethod {

    final SelectServiceAccountOperation getServiceAccountOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        final var username = request.getUsername();
        return pgPool.withTransaction(sqlConnection -> getServiceAccountOperation
                        .selectServiceAccount(sqlConnection, username))
                .map(GetServiceAccountResponse::new);
    }
}
