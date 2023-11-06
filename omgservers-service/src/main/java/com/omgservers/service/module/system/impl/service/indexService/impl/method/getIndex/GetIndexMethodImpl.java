package com.omgservers.service.module.system.impl.service.indexService.impl.method.getIndex;

import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.service.module.system.impl.operation.getIndex.GetIndexOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexMethodImpl implements GetIndexMethod {

    final PgPool pgPool;
    final GetIndexOperation getIndexOperation;

    @Override
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        final var id = request.getId();
        final var deleted = request.getDeleted();
        return pgPool.withTransaction(sqlConnection -> getIndexOperation
                        .getIndex(sqlConnection, id, deleted))
                .map(GetIndexResponse::new);
    }
}
