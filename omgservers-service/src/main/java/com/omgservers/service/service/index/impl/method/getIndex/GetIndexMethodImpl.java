package com.omgservers.service.service.index.impl.method.getIndex;

import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
import com.omgservers.service.service.index.operation.getIndex.GetIndexOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexMethodImpl implements GetIndexMethod {

    final GetIndexOperation getIndexOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        log.debug("Requested, {}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> getIndexOperation
                        .getIndex(sqlConnection, id))
                .map(GetIndexResponse::new);
    }
}
