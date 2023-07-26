package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.deleteIndexMethod;

import com.omgservers.application.module.internalModule.impl.operation.deleteIndexOperation.DeleteIndexOperation;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.DeleteIndexHelpRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteIndexMethodImpl implements DeleteIndexMethod {

    final PgPool pgPool;
    final DeleteIndexOperation deleteIndexOperation;

    @Override
    public Uni<Void> deleteIndex(final DeleteIndexHelpRequest request) {
        DeleteIndexHelpRequest.validate(request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> deleteIndexOperation
                .deleteIndex(sqlConnection, id));
    }
}
