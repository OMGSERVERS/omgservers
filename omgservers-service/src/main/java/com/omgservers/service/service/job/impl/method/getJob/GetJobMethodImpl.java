package com.omgservers.service.service.job.impl.method.getJob;

import com.omgservers.service.service.job.dto.GetJobRequest;
import com.omgservers.service.service.job.dto.GetJobResponse;
import com.omgservers.service.service.job.operation.selectJob.SelectJobOperation;
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
        log.debug("Get job, request={}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectJobOperation
                        .selectJob(sqlConnection, id))
                .map(GetJobResponse::new);
    }
}
