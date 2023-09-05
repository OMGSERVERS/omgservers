package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.module.internal.impl.operation.upsertJob.UpsertJobOperation;
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
    public Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request) {
        SyncJobShardedRequest.validate(request);

        final var job = request.getJob();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertJobOperation.upsertJob(changeContext, sqlConnection, job))
                .map(ChangeContext::getResult)
                .map(SyncJobShardedResponse::new);
    }
}
