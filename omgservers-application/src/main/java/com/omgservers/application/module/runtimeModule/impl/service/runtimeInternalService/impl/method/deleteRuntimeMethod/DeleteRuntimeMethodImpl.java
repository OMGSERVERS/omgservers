package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation.DeleteRuntimeOperation;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMethodImpl implements DeleteRuntimeMethod {

    final InternalModule internalModule;

    final DeleteRuntimeOperation deleteRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        DeleteRuntimeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return deleteRuntime(shard.shard(), id);
                })
                .map(DeleteRuntimeInternalResponse::new);
    }

    Uni<Boolean> deleteRuntime(final int shard, final Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteRuntimeOperation.deleteRuntime(sqlConnection, shard, id)
                .call(deleted -> {
                    final var eventBody = new RuntimeDeletedEventBodyModel(id);
                    final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                    return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                }));
    }
}
