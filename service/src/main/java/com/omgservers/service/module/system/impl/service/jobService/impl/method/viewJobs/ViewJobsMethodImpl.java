package com.omgservers.service.module.system.impl.service.jobService.impl.method.viewJobs;

import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.job.ViewJobsResponse;
import com.omgservers.service.module.system.impl.operation.job.selectJobs.SelectJobsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ViewJobsMethodImpl implements ViewJobsMethod {

    final SelectJobsOperation selectJobsOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewJobsResponse> viewJobs(final ViewJobsRequest request) {
        log.debug("View jobs, request={}", request);

        return pgPool.withTransaction(selectJobsOperation::selectJobs)
                .map(ViewJobsResponse::new);
    }
}
