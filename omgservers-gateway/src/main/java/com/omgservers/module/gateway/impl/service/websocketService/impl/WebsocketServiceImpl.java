package com.omgservers.module.gateway.impl.service.websocketService.impl;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.module.gateway.impl.operation.getGatewayModuleClient.GetGatewayModuleClientOperation;
import com.omgservers.module.gateway.impl.operation.processMessage.ProcessMessageOperation;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.module.gateway.impl.service.connectionService.request.CreateConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.DeleteConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetConnectionRequest;
import com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage.RespondMessageMethod;
import com.omgservers.module.gateway.impl.service.websocketService.WebsocketService;
import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebsocketServiceImpl implements WebsocketService {
    static final int TIMEOUT = 5;

    final SystemModule systemModule;

    final ConnectionService connectionService;

    final RespondMessageMethod respondMessageMethod;

    final GetGatewayModuleClientOperation getGatewayModuleClientOperation;
    final ProcessMessageOperation processMessageOperation;
    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public void cleanUp(@Valid final CleanUpRequest request) {
        final var session = request.getSession();
        final var deleteConnectionHelpRequest = new DeleteConnectionRequest(session);
        final var response = connectionService.deleteConnection(deleteConnectionHelpRequest);

        if (response.getConnectionId().isPresent()) {
            final var connection = response.getConnectionId().get();
            if (response.getAssignedClient().isPresent()) {
                final var assignedClient = response.getAssignedClient().get();
                log.info("Connection was deleted, connection={}, assignedClient={}", connection, assignedClient);

                final var userId = assignedClient.getUserId();
                final var clientId = assignedClient.getClientId();
                final var eventBody = new ClientDisconnectedEventBodyModel(connection, userId, clientId);
                final var event = eventModelFactory.create(eventBody);
                final var fireEventRoutedRequest = new FireEventRequest(event);
                systemModule.getEventService().fireEvent(fireEventRoutedRequest)
                        .await().atMost(Duration.ofSeconds(TIMEOUT));
            } else {
                log.info("There wasn't assigned player, connection was deleted without notification, " +
                        "connection={}", connection);
            }
        }
    }

    @Override
    public void receiveTextMessage(@Valid final ReceiveTextMessageRequest request) {
        final var session = request.getSession();

        final var createConnectionHelpRequest = new CreateConnectionRequest(session);
        connectionService.createConnection(createConnectionHelpRequest);

        final var getConnectionHelpRequest = new GetConnectionRequest(session);
        try {
            final var connectionId = connectionService.getConnection(getConnectionHelpRequest).getConnectionId();
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
