package com.omgservers.service.module.system.impl.service.entityService.impl.method.findEntity;

import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.service.module.system.impl.operation.selectEntityByEntityId.SelectEntityByEntityIdOperation;
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
    public Uni<FindEntityResponse> findEntity(final FindEntityRequest request) {
        log.debug("Find entity, request={}", request);

        final var entityId = request.getEntityId();
        return pgPool.withTransaction(sqlConnection -> selectEntityByEntityIdOperation
                        .selectEntityByEntityId(sqlConnection, entityId))
                .map(FindEntityResponse::new);
    }
}
