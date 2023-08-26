package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod;

import com.omgservers.application.module.userModule.impl.operation.upsertClientOperation.UpsertClientOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.event.body.ClientUpdatedEventBodyModel;
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
    public Uni<SyncClientInternalResponse> syncClient(final SyncClientRoutedRequest request) {
        SyncClientRoutedRequest.validate(request);

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
                .map(SyncClientInternalResponse::new);
    }
}
