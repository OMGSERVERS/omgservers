package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.syncServiceAccountMethod;

import com.omgservers.application.module.internalModule.impl.operation.upsertServiceAccountOperation.UpsertServiceAccountOperation;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
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
    public Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request) {
        SyncServiceAccountHelpRequest.validate(request);

        final var serviceAccount = request.getServiceAccount();
        return pgPool.withTransaction(sqlConnection -> syncServiceAccountOperation
                        .upsertServiceAccount(sqlConnection, serviceAccount))
                .replaceWithVoid();
    }
}
