package com.omgservers.service.module.system.impl.service.indexService.impl.method.getIndex;

import com.omgservers.schema.service.system.GetIndexRequest;
import com.omgservers.schema.service.system.GetIndexResponse;
import com.omgservers.service.module.system.impl.operation.index.getIndex.GetIndexOperation;
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
        log.debug("Get index, request={}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> getIndexOperation
                        .getIndex(sqlConnection, id))
                .map(GetIndexResponse::new);
    }
}
