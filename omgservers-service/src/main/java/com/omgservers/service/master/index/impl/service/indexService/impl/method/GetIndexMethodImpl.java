package com.omgservers.service.master.index.impl.service.indexService.impl.method;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.service.master.index.impl.operation.GetIndexOperation;
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
    public Uni<GetIndexResponse> execute(final GetIndexRequest request) {
        log.trace("{}", request);

        return pgPool.withTransaction(getIndexOperation::execute)
                .map(GetIndexResponse::new);
    }
}
