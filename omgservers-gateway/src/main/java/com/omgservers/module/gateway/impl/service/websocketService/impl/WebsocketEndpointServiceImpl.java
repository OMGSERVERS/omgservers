package com.omgservers.module.gateway.impl.service.websocketService.impl;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.module.gateway.impl.operation.getGatewayModuleClient.GetGatewayModuleClientOperation;
import com.omgservers.module.gateway.impl.operation.processMessage.ProcessMessageOperation;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionHelpService;
import com.omgservers.module.gateway.impl.service.connectionService.request.CreateConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.DeleteConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage.RespondMessageMethod;
import com.omgservers.module.gateway.impl.service.websocketService.WebsocketEndpointService;
import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpHelpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageHelpRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebsocketEndpointServiceImpl implements WebsocketEndpointService {
    static final int TIMEOUT = 5;

    final InternalModule internalModule;

    final ConnectionHelpService connectionHelpService;

    final RespondMessageMethod respondMessageMethod;

    final GetGatewayModuleClientOperation getGatewayModuleClientOperation;
    final ProcessMessageOperation processMessageOperation;
    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public void cleanUp(final CleanUpHelpRequest request) {
        CleanUpHelpRequest.validate(request);

        final var session = request.getSession();
        final var deleteConnectionHelpRequest = new DeleteConnectionHelpRequest(session);
        final var response = connectionHelpService.deleteConnection(deleteConnectionHelpRequest);

        if (response.getConnectionId().isPresent()) {
            final var connection = response.getConnectionId().get();
            if (response.getAssignedPlayer().isPresent()) {
                final var assignedPlayer = response.getAssignedPlayer().get();
                log.info("Connection was deleted, connection={}, assignedPlayer={}", connection, assignedPlayer);

                final var userId = assignedPlayer.getUserId();
                final var clientId = assignedPlayer.getClientId();
                final var eventBody = new ClientDisconnectedEventBodyModel(connection, userId, clientId);
                final var event = eventModelFactory.create(eventBody);
                final var fireEventRoutedRequest = new FireEventShardedRequest(event);
                internalModule.getEventShardedService().fireEvent(fireEventRoutedRequest)
                        .await().atMost(Duration.ofSeconds(TIMEOUT));
            } else {
                log.info("There wasn't assigned player, connection was deleted without notification, " +
                        "connection={}", connection);
            }
        }
    }

    @Override
    public void receiveTextMessage(final ReceiveTextMessageHelpRequest request) {
        ReceiveTextMessageHelpRequest.validate(request);
        final var session = request.getSession();

        final var createConnectionHelpRequest = new CreateConnectionHelpRequest(session);
        connectionHelpService.createConnection(createConnectionHelpRequest);

        final var getConnectionHelpRequest = new GetConnectionHelpRequest(session);
        try {
            final var connectionId = connectionHelpService.getConnection(getConnectionHelpRequest).getConnectionId();
            final var messageString = request.getMessage();
            processMessageOperation.processMessage(connectionId, messageString)
                    .await().atMost(Duration.ofSeconds(TIMEOUT));
        } catch (Exception e) {
            closeSession(session, e.getMessage());
        }
    }

    void closeSession(Session session, String reason) {
        try {
            log.error("Session will be closed, {}", reason);
            session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, reason));
        } catch (IOException e) {
            log.error("Session wasn't closed properly, {}", e.getMessage());
        }
    }
}
