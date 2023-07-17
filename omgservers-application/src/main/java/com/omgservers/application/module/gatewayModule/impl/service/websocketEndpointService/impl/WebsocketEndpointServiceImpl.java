package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.impl;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation.GetGatewayServiceApiClientOperation;
import com.omgservers.application.module.gatewayModule.impl.operation.processMessageOperation.ProcessMessageOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.WebsocketEndpointService;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.assignPlayerMethod.AssignPlayerMethod;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.respondMessageMethod.RespondMessageMethod;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.DeleteConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CreateConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebsocketEndpointServiceImpl implements WebsocketEndpointService {

    final ConnectionHelpService connectionHelpService;
    final ClientInternalService clientInternalService;

    final RespondMessageMethod respondMessageMethod;
    final AssignPlayerMethod assignPlayerMethod;

    final GetGatewayServiceApiClientOperation getGatewayServiceApiClientOperation;
    final ProcessMessageOperation processMessageOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public void createConnection(final CreateConnectionHelpRequest request) {
        CreateConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var createConnectionHelpRequest = new com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.CreateConnectionHelpRequest(session);
        try {
            connectionHelpService.createConnection(createConnectionHelpRequest);
        } catch (ServerSideConflictException e) {
            // TODO: close session ??
            log.error("Request failed, skip operation, {}", e.getMessage());
        }
    }

    @Override
    public void deleteConnection(final DeleteConnectionHelpRequest request) {
        DeleteConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var deleteConnectionHelpRequest = new com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.DeleteConnectionHelpRequest(session);
        connectionHelpService.deleteConnection(deleteConnectionHelpRequest);
    }

    @Override
    public void receiveTextMessage(final ReceiveTextMessageHelpRequest request) {
        ReceiveTextMessageHelpRequest.validate(request);

        final var session = request.getSession();
        final var getConnectionHelpRequest = new GetConnectionHelpRequest(session);
        try {
            final var connection = connectionHelpService.getConnection(getConnectionHelpRequest).getConnection();
            final var messageString = request.getMessage();
            processMessageOperation.processMessage(connection, messageString);
        } catch (ServerSideNotFoundException e) {
            // TODO: close session ??
            log.error("Request failed, skip operation, {}", e.getMessage());
        }
    }
}
