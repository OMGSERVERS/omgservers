package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod;

import com.omgservers.application.module.internalModule.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.ClientUpdatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.upsertClientOperation.UpsertClientOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.SyncClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.SyncClientInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMethodImpl implements SyncClientMethod {

    final UpsertClientOperation upsertClientOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientInternalResponse> syncClient(final SyncClientInternalRequest request) {
        SyncClientInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var client = request.getClient();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncClientInternalResponse::new);
    }
}
