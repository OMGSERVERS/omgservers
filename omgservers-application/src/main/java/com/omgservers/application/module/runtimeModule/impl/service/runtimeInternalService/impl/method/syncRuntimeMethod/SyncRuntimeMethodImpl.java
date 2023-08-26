package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.event.body.RuntimeUpdatedEventBodyModel;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request) {
        SyncRuntimeShardRequest.validate(request);

        final var runtime = request.getRuntime();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertRuntimeOperation
                                .upsertRuntime(sqlConnection, shardModel.shard(), runtime),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Runtime was created, runtime=" + runtime);
                            } else {
                                return logModelFactory.create("Runtime was update, runtime=" + runtime);
                            }
                        },
                        inserted -> {
                            final var id = runtime.getId();
                            final var matchmakerId = runtime.getMatchmakerId();
                            final var matchId = runtime.getMatchId();
                            if (inserted) {
                                return new RuntimeCreatedEventBodyModel(id, matchmakerId, matchId);
                            } else {
                                return new RuntimeUpdatedEventBodyModel(id, matchmakerId, matchId);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncRuntimeInternalResponse::new);
    }
}
