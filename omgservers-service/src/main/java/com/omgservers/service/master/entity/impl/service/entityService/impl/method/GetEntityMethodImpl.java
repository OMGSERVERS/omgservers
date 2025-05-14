package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.service.master.entity.impl.operation.SelectEntityOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetEntityMethodImpl implements GetEntityMethod {

    final SelectEntityOperation selectEntityOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetEntityResponse> execute(final GetEntityRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectEntityOperation
                        .execute(sqlConnection, id))
                .map(GetEntityResponse::new);
    }
}
