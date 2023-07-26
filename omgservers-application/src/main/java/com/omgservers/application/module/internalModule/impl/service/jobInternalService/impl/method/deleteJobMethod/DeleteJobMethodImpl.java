package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.deleteJobMethod;

import com.omgservers.application.module.internalModule.impl.operation.deleteJobOperation.DeleteJobOperation;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.JobDeletedEventBodyModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobMethodImpl implements DeleteJobMethod {

    final EventHelpService eventInternalService;

    final CheckShardOperation checkShardOperation;
    final DeleteJobOperation deleteJobOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request) {
        DeleteJobInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var shardKey = request.getShardKey();
                    final var entity = request.getEntity();
                    return deleteJob(shardKey, entity);
                })
                .map(DeleteJobInternalResponse::new);
    }

    Uni<Boolean> deleteJob(final Long shardKey, final Long entity) {
        return pgPool.withTransaction(sqlConnection -> deleteJobOperation.deleteJob(sqlConnection, shardKey, entity)
                .call(deleted -> {
                    if (deleted) {
                        final var eventBody = new JobDeletedEventBodyModel(shardKey, entity);
                        final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                        return eventInternalService.insertEvent(insertEventInternalRequest);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                }));
    }
}
