package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.syncJobMethod;

import com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation.UpsertJobOperation;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.job.JobModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncJobMethodImpl implements SyncJobMethod {

    final EventHelpService eventInternalService;

    final CheckShardOperation checkShardOperation;
    final UpsertJobOperation upsertJobOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request) {
        SyncJobInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var job = request.getJob();
                    return syncJob(job);
                })
                .map(SyncJobInternalResponse::new);
    }

    Uni<Boolean> syncJob(final JobModel job) {
        return pgPool.withTransaction(sqlConnection -> upsertJobOperation.upsertJob(sqlConnection, job)
                .call(inserted -> {
                    if (inserted) {
                        final var shardKey = job.getShardKey();
                        final var entity = job.getEntity();
                        final var type = job.getType();
                        final var origin = JobCreatedEventBodyModel.createEvent(shardKey, entity, type);
                        final var event = EventCreatedEventBodyModel.createEvent(origin);
                        final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                        return eventInternalService.insertEvent(insertEventInternalRequest);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                }));
    }
}
