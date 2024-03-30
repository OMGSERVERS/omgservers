package com.omgservers.service.module.system.impl.service.indexService.impl.method.findIndex;

import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.service.module.system.impl.operation.index.selectIndexByName.SelectIndexByNameOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindIndexMethodImpl implements FindIndexMethod {

    final SelectIndexByNameOperation selectIndexByNameOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        log.trace("Find index, request={}", request);

        final var name = request.getName();
        return pgPool.withTransaction(sqlConnection -> selectIndexByNameOperation
                        .selectIndexByName(sqlConnection, name))
                .map(FindIndexResponse::new);
    }
}
