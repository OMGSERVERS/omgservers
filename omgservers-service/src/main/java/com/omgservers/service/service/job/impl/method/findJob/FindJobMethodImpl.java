package com.omgservers.service.service.job.impl.method.findJob;

import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
import com.omgservers.service.service.job.operation.selectJobByEntityId.SelectJobByEntityIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindJobMethodImpl implements FindJobMethod {

    final SelectJobByEntityIdOperation selectJobByEntityIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindJobResponse> findJob(final FindJobRequest request) {
        log.debug("Find job, request={}", request);

        final var entityId = request.getEntityId();
        return pgPool.withTransaction(sqlConnection -> selectJobByEntityIdOperation
                        .selectJobByEntityId(sqlConnection, entityId))
                .map(FindJobResponse::new);
    }
}
