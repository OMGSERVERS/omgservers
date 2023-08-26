package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob;

import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.operation.upsertJob.UpsertJobOperation;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
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
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request) {
        SyncJobShardRequest.validate(request);

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
                .map(SyncJobRoutedResponse::new);
    }
}
