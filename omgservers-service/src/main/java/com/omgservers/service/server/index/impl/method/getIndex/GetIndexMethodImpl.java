package com.omgservers.service.server.index.impl.method.getIndex;

import com.omgservers.service.server.index.dto.GetIndexRequest;
import com.omgservers.service.server.index.dto.GetIndexResponse;
import com.omgservers.service.server.index.operation.GetIndexOperation;
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
        log.trace("{}", request);

        return pgPool.withTransaction(getIndexOperation::getIndex)
                .map(GetIndexResponse::new);
    }
}
