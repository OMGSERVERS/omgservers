package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob;

import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.model.event.body.JobUpdatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncJobMethodImpl implements SyncJobMethod {

    final InternalModule internalModule;

    final UpsertJobOperation upsertJobOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request) {
        SyncJobShardedRequest.validate(request);

        final var job = request.getJob();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertJobOperation
                                .upsertJob(sqlConnection, job),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Job was created, job=" + job);
                            } else {
                                return logModelFactory.create("Job was updated, job=" + job);
                            }
                        },
                        inserted -> {
                            final var shardKey = job.getShardKey();
                            final var entity = job.getEntity();
                            final var type = job.getType();
                            if (inserted) {
                                return new JobCreatedEventBodyModel(shardKey, entity, type);
                            } else {
                                return new JobUpdatedEventBodyModel(shardKey, entity, type);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncJobShardedResponse::new);
    }
}
