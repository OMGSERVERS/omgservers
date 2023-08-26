package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncCommandMethod;

import com.omgservers.application.module.runtimeModule.impl.operation.upsertCommandOperation.UpsertCommandOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.runtimeModule.SyncCommandShardRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
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
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request) {
        SyncCommandShardRequest.validate(request);

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
