package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncCommandMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertCommandOperation.UpsertCommandOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.runtimeModule.SyncCommandInternalRequest;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request) {
        SyncCommandInternalRequest.validate(request);

        final var command = request.getCommand();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> upsertCommandOperation
                                .upsertCommand(sqlConnection, shardModel.shard(), command),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Command was created, command=" + command);
                            } else {
                                return logModelFactory.create("Command was update, command=" + command);
                            }
                        }
                )
                .map(SyncCommandInternalResponse::new);
    }
}
