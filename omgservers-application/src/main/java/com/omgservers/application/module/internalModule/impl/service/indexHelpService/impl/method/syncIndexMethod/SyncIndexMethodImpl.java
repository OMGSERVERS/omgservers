package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.syncIndexMethod;

import com.omgservers.application.module.internalModule.impl.operation.upsertIndexOperation.UpsertIndexOperation;
import com.omgservers.application.module.internalModule.impl.operation.validateIndexOperation.ValidateIndexOperation;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexMethodImpl implements SyncIndexMethod {

    final PgPool pgPool;
    final ValidateIndexOperation validateIndexOperation;
    final UpsertIndexOperation syncIndexOperation;

    @Override
    public Uni<Void> syncIndex(SyncIndexHelpRequest request) {
        SyncIndexHelpRequest.validate(request);

        final var index = request.getIndex();
        return Uni.createFrom().item(index)
                .invoke(validateIndexOperation::validateIndex)
                .flatMap(validatedIndex -> pgPool.withTransaction(sqlConnection -> syncIndexOperation
                        .upsertIndex(sqlConnection, validatedIndex)))
                .replaceWithVoid();
    }
}
