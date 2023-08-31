package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntimeCommand;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeCommandMethodImpl implements SyncRuntimeCommandMethod {

    final InternalModule internalModule;

    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request) {
        SyncRuntimeCommandShardedRequest.validate(request);

        final var runtimeCommand = request.getRuntimeCommand();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertRuntimeCommandOperation
                                .upsertRuntimeCommand(sqlConnection, shardModel.shard(), runtimeCommand),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Runtime command was created, " +
                                        "runtimeCommand=" + runtimeCommand);
                            } else {
                                return logModelFactory.create("Runtime command was updated, " +
                                        "runtimeCommand=" + runtimeCommand);
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncRuntimeCommandShardedResponse::new);
    }
}
