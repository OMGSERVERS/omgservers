package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.createClientMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.internalModule.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.application.module.userModule.impl.operation.insertClientOperation.InsertClientOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateClientMethodImpl implements CreateClientMethod {

    final InternalModule internalModule;
    final CheckShardOperation checkShardOperation;
    final InsertClientOperation insertClientOperation;
    final PgPool pgPool;

    @Override
    public Uni<CreateClientInternalResponse> createClient(final CreateClientInternalRequest request) {
        CreateClientInternalRequest.validate(request);

        final var user = request.getUser();
        final var player = request.getPlayer();
        final var server = request.getServer();
        final var connection = request.getConnection();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var client = ClientModel.create(player, server, connection);
                    return createClient(shardModel.shard(), user, client)
                            .replaceWith(client);
                })
                .map(CreateClientInternalResponse::new);
    }

    Uni<Void> createClient(Integer shard, UUID user, ClientModel client) {
        return pgPool.withTransaction(sqlConnection ->
                insertClientOperation.insertClient(sqlConnection, shard, client)
                        .call(inserted -> {
                            final var uuid = client.getUuid();
                            final var origin = ClientCreatedEventBodyModel.createEvent(user, uuid);
                            final var event = EventCreatedEventBodyModel.createEvent(origin);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                        }));
    }
}
