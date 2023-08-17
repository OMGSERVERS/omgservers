package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.deleteClientOperation.DeleteClientOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientMethodImpl implements DeleteClientMethod {

    final InternalModule internalModule;

    final DeleteClientOperation deleteClientOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteClient(final DeleteClientInternalRequest request) {
        DeleteClientInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var client = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> deleteClientOperation
                                    .deleteClient(sqlConnection, shard, client)
                                    .call(deleted -> {
                                        final var syncLog = logModelFactory.create("Client was deleted, " +
                                                "client=" + client);
                                        final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                        return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                                    }))
                            .replaceWithVoid();
                });
    }
}
