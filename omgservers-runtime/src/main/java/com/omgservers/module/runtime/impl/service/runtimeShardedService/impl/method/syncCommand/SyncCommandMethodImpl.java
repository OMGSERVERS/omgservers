package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncCommand;

import com.omgservers.module.runtime.impl.operation.upsertCommand.UpsertCommandOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.runtime.SyncCommandShardedRequest;
import com.omgservers.dto.runtime.SyncCommandInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncCommandMethodImpl implements SyncCommandMethod {

    final InternalModule internalModule;

    final UpsertCommandOperation upsertCommandOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardedRequest request) {
        SyncCommandShardedRequest.validate(request);

        final var command = request.getCommand();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertCommandOperation
                                .upsertCommand(sqlConnection, shardModel.shard(), command),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Command was created, command=" + command);
                            } else {
                                return logModelFactory.create("Command was update, command=" + command);
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncCommandInternalResponse::new);
    }
}
