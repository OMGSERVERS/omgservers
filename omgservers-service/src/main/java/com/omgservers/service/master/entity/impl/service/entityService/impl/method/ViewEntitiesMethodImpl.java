package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import com.omgservers.service.master.entity.impl.operation.SelectActiveEntitiesOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewEntitiesMethodImpl implements ViewEntitiesMethod {

    final SelectActiveEntitiesOperation selectActiveEntitiesOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewEntitiesResponse> execute(final ViewEntitiesRequest request) {
        log.debug("{}", request);

        return pgPool.withTransaction(selectActiveEntitiesOperation::execute)
                .map(ViewEntitiesResponse::new);
    }
}
