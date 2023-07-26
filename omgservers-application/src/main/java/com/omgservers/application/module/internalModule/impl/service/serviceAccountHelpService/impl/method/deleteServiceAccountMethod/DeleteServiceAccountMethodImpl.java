package com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.impl.method.deleteServiceAccountMethod;

import com.omgservers.application.module.internalModule.impl.operation.deleteServiceAccountOperation.DeleteServiceAccountOperation;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.DeleteServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteServiceAccountMethodImpl implements DeleteServiceAccountMethod {

    final DeleteServiceAccountOperation deleteServiceAccountOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> deleteServiceAccount(DeleteServiceAccountHelpRequest request) {
        DeleteServiceAccountHelpRequest.validate(request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> deleteServiceAccountOperation
                .deleteServiceAccount(sqlConnection, id));
    }
}
