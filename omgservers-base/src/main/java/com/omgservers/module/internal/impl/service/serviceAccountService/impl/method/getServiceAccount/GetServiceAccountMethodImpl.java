package com.omgservers.module.internal.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.module.internal.impl.operation.selectServiceAccount.SelectServiceAccountOperation;
import com.omgservers.dto.internal.GetServiceAccountRequest;
import com.omgservers.dto.internal.GetServiceAccountResponse;
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
        GetServiceAccountRequest.validate(request);

        final var username = request.getUsername();
        return pgPool.withTransaction(sqlConnection -> getServiceAccountOperation
                        .selectServiceAccount(sqlConnection, username))
                .map(GetServiceAccountResponse::new);
    }
}
