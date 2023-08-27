package com.omgservers.module.internal.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.module.internal.impl.operation.upsertIndex.UpsertIndexOperation;
import com.omgservers.module.internal.impl.operation.validateIndex.ValidateIndexOperation;
import com.omgservers.dto.internal.SyncIndexRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexMethodImpl implements SyncIndexMethod {

    final ValidateIndexOperation validateIndexOperation;
    final UpsertIndexOperation syncIndexOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        SyncIndexRequest.validate(request);

        final var index = request.getIndex();
        return Uni.createFrom().item(index)
                .invoke(validateIndexOperation::validateIndex)
                .flatMap(validatedIndex -> pgPool.withTransaction(sqlConnection -> syncIndexOperation
                        .upsertIndex(sqlConnection, validatedIndex)))
                .replaceWithVoid();
    }
}
