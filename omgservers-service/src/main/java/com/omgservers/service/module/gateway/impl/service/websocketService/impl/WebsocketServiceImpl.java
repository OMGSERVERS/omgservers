package com.omgservers.service.module.gateway.impl.service.websocketService.impl;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.gateway.impl.operation.processMessage.ProcessMessageOperation;
import com.omgservers.service.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.CreateConnectionRequest;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.DeleteConnectionRequest;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.GetConnectionRequest;
import com.omgservers.service.module.gateway.impl.service.websocketService.WebsocketService;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.CloseSessionRequest;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;
import com.omgservers.service.module.system.SystemModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.websocket.CloseReason;
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

    final ProcessMessageOperation processMessageOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public void cleanUp(@Valid final CleanUpRequest request) {
        final var session = request.getSession();
        final var deleteConnectionHelpRequest = new DeleteConnectionRequest(session);
        final var response = connectionService.deleteConnection(deleteConnectionHelpRequest);

        final var connectionIdOptional = response.getConnectionId();
        if (connectionIdOptional.isPresent()) {
            final var connection = connectionIdOptional.get();

            final var assignedClientOptional = response.getAssignedClient();
            if (assignedClientOptional.isPresent()) {
                final var assignedClient = assignedClientOptional.get();

                final var userId = assignedClient.getUserId();
                final var clientId = assignedClient.getClientId();
                final var eventBody = new ClientDisconnectedEventBodyModel(connection, userId, clientId);
                final var event = eventModelFactory.create(eventBody);
                final var syncEventRequest = new SyncEventRequest(event);
                systemModule.getEventService().syncEvent(syncEventRequest)
                        .await().atMost(Duration.ofSeconds(TIMEOUT));
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
            closeSession(new CloseSessionRequest(session, e.getMessage()));
        }
    }

    @Override
    public void closeSession(@Valid final CloseSessionRequest request) {
        final var session = request.getSession();
        final var reason = request.getReason();

        try {
            log.error("Session will be closed, sessionId={}, {}", session.getId(), reason);
            session.close(new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE, reason));
        } catch (IOException e) {
            log.error("Session wasn't closed properly, {}", e.getMessage());
        }
    }
}
