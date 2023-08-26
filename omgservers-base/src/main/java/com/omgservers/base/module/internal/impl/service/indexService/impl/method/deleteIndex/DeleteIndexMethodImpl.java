package com.omgservers.base.module.internal.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.base.module.internal.impl.operation.deleteIndex.DeleteIndexOperation;
import com.omgservers.dto.internalModule.DeleteIndexRequest;
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
    public Uni<Void> deleteIndex(final DeleteIndexRequest request) {
        DeleteIndexRequest.validate(request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> deleteIndexOperation
                .deleteIndex(sqlConnection, id));
    }
}
