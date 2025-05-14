package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.service.master.entity.impl.operation.SelectEntityByEntityIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindEntityMethodImpl implements FindEntityMethod {

    final SelectEntityByEntityIdOperation selectEntityByEntityIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindEntityResponse> execute(final FindEntityRequest request) {
        log.trace("{}", request);

        final var entityId = request.getEntityId();
        return pgPool.withTransaction(sqlConnection -> selectEntityByEntityIdOperation
                        .execute(sqlConnection,
                                entityId))
                .map(FindEntityResponse::new);
    }
}
