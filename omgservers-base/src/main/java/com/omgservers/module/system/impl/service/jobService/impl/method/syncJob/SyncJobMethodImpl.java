package com.omgservers.module.system.impl.service.jobService.impl.method.syncJob;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.internal.SyncJobResponse;
import com.omgservers.module.system.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncJobMethodImpl implements SyncJobMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertJobOperation upsertJobOperation;

    @Override
    public Uni<SyncJobResponse> syncJob(SyncJobRequest request) {
        SyncJobRequest.validate(request);

        final var job = request.getJob();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertJobOperation.upsertJob(changeContext, sqlConnection, job))
                .map(ChangeContext::getResult)
                .map(SyncJobResponse::new);
    }
}
