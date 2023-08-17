package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
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
class SyncRuntimeMethodImpl implements SyncRuntimeMethod {

    final InternalModule internalModule;

    final UpsertRuntimeOperation upsertRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> syncRuntime(SyncRuntimeInternalRequest request) {
        SyncRuntimeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtime = request.getRuntime();
                    return syncRuntime(shard.shard(), runtime);
                });
    }

    Uni<Void> syncRuntime(final int shard, final RuntimeModel runtime) {
        return pgPool.withTransaction(sqlConnection -> upsertRuntimeOperation
                        .upsertRuntime(sqlConnection, shard, runtime)
                        .call(inserted -> {
                            if (inserted) {
                                final var id = runtime.getId();
                                final var matchmakerId = runtime.getMatchmakerId();
                                final var matchId = runtime.getMatchId();
                                final var eventBody = new RuntimeCreatedEventBodyModel(id, matchmakerId, matchId);
                                final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        })
                        .call(inserted -> {
                            final var syncLog = logModelFactory.create("Runtime was sync, runtime=" + runtime);
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }))
                .replaceWithVoid();
    }
}
