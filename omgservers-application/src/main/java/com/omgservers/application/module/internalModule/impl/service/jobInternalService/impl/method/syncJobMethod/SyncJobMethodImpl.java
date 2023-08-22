package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.syncJobMethod;

import com.omgservers.application.module.internalModule.impl.operation.upsertJobOperation.UpsertJobOperation;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.model.event.body.JobCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.JobUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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

    final UpsertJobOperation upsertJobOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request) {
        SyncJobInternalRequest.validate(request);

        final var job = request.getJob();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncJobInternalResponse::new);
    }
}
