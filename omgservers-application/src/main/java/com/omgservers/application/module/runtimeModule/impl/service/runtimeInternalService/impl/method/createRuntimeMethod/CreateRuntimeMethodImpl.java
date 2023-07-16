package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.createRuntimeMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation.InsertRuntimeOperation;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateRuntimeMethodImpl implements CreateRuntimeMethod {

    final InternalModule internalModule;

    final InsertRuntimeOperation insertRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> createRuntime(CreateRuntimeInternalRequest request) {
        CreateRuntimeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtime = request.getRuntime();
                    return createRuntime(shard.shard(), runtime);
                });
    }

    Uni<Void> createRuntime(final int shard, final RuntimeModel runtime) {
        return pgPool.withTransaction(sqlConnection -> insertRuntimeOperation.insertRuntime(sqlConnection, shard, runtime)
                        .call(voidItem -> {
                            final var uuid = runtime.getUuid();
                            final var matchmaker = runtime.getMatchmaker();
                            final var match = runtime.getMatch();
                            final var origin = RuntimeCreatedEventBodyModel.createEvent(uuid, matchmaker, match);
                            final var event = EventCreatedEventBodyModel.createEvent(origin);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                        }))
                .replaceWithVoid();
    }
}
