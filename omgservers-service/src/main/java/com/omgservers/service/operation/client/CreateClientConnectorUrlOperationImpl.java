package com.omgservers.service.operation.client;

import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateClientConnectorUrlOperationImpl implements CreateClientConnectorUrlOperation {

    static final String WEBSOCKET_ENDPOINT = "/connector/v1/entrypoint/websocket/endpoint";
    static final String WS_TOKEN_PARAM = "ws_token";

    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public URI execute(final Long clientId) {
        final var connectorUri = getServiceConfigOperation.getServiceConfig().client().connectorUri();
        final var wsToken = issueJwtTokenOperation
                .issueConnectorClientWebsocketToken(clientId);

        final var connectionUrl = UriBuilder.fromUri(connectorUri)
                .path(WEBSOCKET_ENDPOINT)
                .queryParam(WS_TOKEN_PARAM, wsToken)
                .build();

        return connectionUrl;
    }
}
