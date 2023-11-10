package com.omgservers.service.module.system.impl.service.jobService.impl.method.getJob;

import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.service.module.system.impl.operation.selectJob.SelectJobOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetJobMethodImpl implements GetJobMethod {

    final SelectJobOperation selectJobOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetJobResponse> getJob(final GetJobRequest request) {
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectJobOperation
                        .selectJob(sqlConnection, id))
                .map(GetJobResponse::new);
    }
}
