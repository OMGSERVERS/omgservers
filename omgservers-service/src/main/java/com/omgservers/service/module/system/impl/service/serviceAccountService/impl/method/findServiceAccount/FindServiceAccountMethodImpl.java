package com.omgservers.service.module.system.impl.service.serviceAccountService.impl.method.findServiceAccount;

import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.service.module.system.impl.operation.serviceAccount.selectServiceAccountByUsername.SelectServiceAccountByUsernameOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindServiceAccountMethodImpl implements FindServiceAccountMethod {

    final SelectServiceAccountByUsernameOperation selectServiceAccountByUsernameOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindServiceAccountResponse> findServiceAccount(final FindServiceAccountRequest request) {
        log.debug("Find service account, request={}", request);

        final var username = request.getUsername();
        return pgPool.withTransaction(sqlConnection -> selectServiceAccountByUsernameOperation
                        .selectServiceAccountByUsername(sqlConnection, username))
                .map(FindServiceAccountResponse::new);
    }
}
