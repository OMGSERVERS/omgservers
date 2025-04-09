package com.omgservers.service.server.job.impl.method.syncJob;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.job.dto.SyncJobRequest;
import com.omgservers.service.server.job.dto.SyncJobResponse;
import com.omgservers.service.server.job.operation.UpsertJobOperation;
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
