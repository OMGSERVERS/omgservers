package com.omgservers.service.module.system.impl.service.jobService.impl.method.syncJob;

import com.omgservers.schema.service.system.job.SyncJobRequest;
import com.omgservers.schema.service.system.job.SyncJobResponse;
import com.omgservers.service.module.system.impl.operation.job.upsertJob.UpsertJobOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncJobMethodImpl implements SyncJobMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertJobOperation upsertJobOperation;

    @Override
    public Uni<SyncJobResponse> syncJob(final SyncJobRequest request) {
        log.debug("Sync job, request={}", request);

        final var job = request.getJob();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertJobOperation.upsertJob(changeContext, sqlConnection, job))
                .map(ChangeContext::getResult)
                .map(SyncJobResponse::new);
    }
}
