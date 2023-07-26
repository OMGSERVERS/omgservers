package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.impl;

import com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation.GetGatewayServiceApiClientOperation;
import com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation.ProcessMessageOperation;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.CreateConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.DeleteConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CleanUpHelpRequest;
import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.WebsocketEndpointService;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.respondMessageMethod.RespondMessageMethod;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.HandleSessionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebsocketEndpointServiceImpl implements WebsocketEndpointService {
    static final int TIMEOUT = 5;

    final InternalModule internalModule;

    final ConnectionHelpService connectionHelpService;
    final ClientInternalService clientInternalService;

    final RespondMessageMethod respondMessageMethod;

    final GetGatewayServiceApiClientOperation getGatewayServiceApiClientOperation;
    final ProcessMessageOperation processMessageOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public void handleSession(final HandleSessionHelpRequest request) {
        HandleSessionHelpRequest.validate(request);

        final var session = request.getSession();
        final var createConnectionHelpRequest = new CreateConnectionHelpRequest(session);
        try {
            connectionHelpService.createConnection(createConnectionHelpRequest);
        } catch (Exception e) {
            closeSession(session, e.getMessage());
        }
    }

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
                final var fireEventInternalRequest = new FireEventHelpRequest(eventBody);
                internalModule.getEventHelpService().fireEvent(fireEventInternalRequest)
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
