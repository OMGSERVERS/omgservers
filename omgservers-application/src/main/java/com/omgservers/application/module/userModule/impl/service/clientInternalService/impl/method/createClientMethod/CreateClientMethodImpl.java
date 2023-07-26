package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.createClientMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.internalModule.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.application.module.userModule.impl.operation.insertClientOperation.InsertClientOperation;
import com.omgservers.application.module.userModule.model.client.ClientModelFactory;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateClientMethodImpl implements CreateClientMethod {

    final InternalModule internalModule;

    final InsertClientOperation insertClientOperation;
    final CheckShardOperation checkShardOperation;
    final ClientModelFactory clientModelFactory;
    final GenerateIdOperation generateIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<CreateClientInternalResponse> createClient(final CreateClientInternalRequest request) {
        CreateClientInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var server = request.getServer();
        final var connectionId = request.getConnectionId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var client = clientModelFactory.create(playerId, server, connectionId);
                    return createClient(shardModel.shard(), userId, client)
                            .replaceWith(client);
                })
                .map(CreateClientInternalResponse::new);
    }

    Uni<Void> createClient(Integer shard, Long userId, ClientModel client) {
        return pgPool.withTransaction(sqlConnection ->
                insertClientOperation.insertClient(sqlConnection, shard, client)
                        .call(inserted -> {
                            final var id = client.getId();
                            final var eventBody = new ClientCreatedEventBodyModel(userId, id);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                        }));
    }
}
