package com.omgservers.service.server.job.impl.method.getJob;

import com.omgservers.service.server.job.dto.GetJobRequest;
import com.omgservers.service.server.job.dto.GetJobResponse;
import com.omgservers.service.server.job.operation.SelectJobOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetJobMethodImpl implements GetJobMethod {

    final SelectJobOperation selectJobOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetJobResponse> getJob(final GetJobRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectJobOperation
                        .selectJob(sqlConnection, id))
                .map(GetJobResponse::new);
    }
}
