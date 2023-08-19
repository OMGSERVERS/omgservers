package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.upsertClientOperation.UpsertClientOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.userModule.model.client.ClientModelFactory;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMethodImpl implements SyncClientMethod {

    final InternalModule internalModule;

    final UpsertClientOperation insertClientOperation;
    final CheckShardOperation checkShardOperation;

    final ClientModelFactory clientModelFactory;
    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientInternalResponse> syncClient(final SyncClientInternalRequest request) {
        SyncClientInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var client = request.getClient();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> createClient(shardModel.shard(), userId, client))
                .map(SyncClientInternalResponse::new);
    }

    Uni<Boolean> createClient(Integer shard, Long userId, ClientModel client) {
        return pgPool.withTransaction(sqlConnection ->
                insertClientOperation.upsertClient(sqlConnection, shard, client)
                        .call(inserted -> {
                            final var id = client.getId();
                            final var eventBody = new ClientCreatedEventBodyModel(userId, id);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                        })
                        .call(inserted -> {
                            final LogModel syncLog;
                            if (inserted) {
                                syncLog = logModelFactory.create("Client was created, client=" + client);
                            } else {
                                syncLog = logModelFactory.create("Client was updated, client=" + client);
                            }
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }));
    }
}
