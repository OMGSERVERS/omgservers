package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.getServiceAccount;

import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.service.module.system.impl.operation.selectServiceAccount.SelectServiceAccountOperation;
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

    final SelectServiceAccountOperation selectServiceAccountOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(final GetServiceAccountRequest request) {
        final var id = request.getId();
        final var deleted = request.getDeleted();
        return pgPool.withTransaction(sqlConnection -> selectServiceAccountOperation
                        .selectServiceAccount(sqlConnection, id, deleted))
                .map(GetServiceAccountResponse::new);
    }
}
