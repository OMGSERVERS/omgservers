package com.omgservers.module.user.impl.service.clientShardedService.impl.method.syncClient;

import com.omgservers.ChangeWithEventRequest;
import com.omgservers.ChangeWithEventResponse;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientUpdatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.upsertClient.UpsertClientOperation;
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

    final UpsertClientOperation upsertClientOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientShardedResponse> syncClient(final SyncClientShardedRequest request) {
        SyncClientShardedRequest.validate(request);

        final var userId = request.getUserId();
        final var client = request.getClient();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertClientOperation
                                .upsertClient(sqlConnection, shardModel.shard(), client),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Client was created, client=" + client);
                            } else {
                                return logModelFactory.create("Client was updated, client=" + client);
                            }
                        },
                        inserted -> {
                            final var id = client.getId();
                            if (inserted) {
                                return new ClientCreatedEventBodyModel(userId, id);
                            } else {
                                return new ClientUpdatedEventBodyModel(userId, id);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncClientShardedResponse::new);
    }
}
