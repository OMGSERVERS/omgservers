package com.omgservers.service.server.job.impl.method.viewJobs;

import com.omgservers.service.server.job.dto.ViewJobsRequest;
import com.omgservers.service.server.job.dto.ViewJobsResponse;
import com.omgservers.service.server.job.operation.SelectJobsOperation;
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
        log.trace("{}", request);

        return pgPool.withTransaction(selectJobsOperation::selectJobs)
                .map(ViewJobsResponse::new);
    }
}
