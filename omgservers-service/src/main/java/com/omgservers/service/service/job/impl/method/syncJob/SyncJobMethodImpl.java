package com.omgservers.service.service.job.impl.method.syncJob;

import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import com.omgservers.service.service.job.operation.upsertJob.UpsertJobOperation;
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
        log.trace("{}", request);

        final var job = request.getJob();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertJobOperation.upsertJob(changeContext, sqlConnection, job))
                .map(ChangeContext::getResult)
                .map(SyncJobResponse::new);
    }
}
