package com.omgservers.base.module.internal.impl.service.serviceAccountService.impl.method.syncServiceAccount;

import com.omgservers.base.module.internal.impl.operation.upsertServiceAccount.UpsertServiceAccountOperation;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncServiceAccountMethodImpl implements SyncServiceAccountMethod {

    final UpsertServiceAccountOperation syncServiceAccountOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        SyncServiceAccountRequest.validate(request);

        final var serviceAccount = request.getServiceAccount();
        return pgPool.withTransaction(sqlConnection -> syncServiceAccountOperation
                        .upsertServiceAccount(sqlConnection, serviceAccount))
                .replaceWithVoid();
    }
}
