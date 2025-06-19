package com.omgservers.service.operation.client;

import com.omgservers.schema.security.WebSocketConfig;
import com.omgservers.service.operation.security.CreateQuarkusHeaderProtocolOperation;
import com.omgservers.service.operation.security.IssueJwtTokenOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateConnectorClientWebSocketConfigOperationImpl implements CreateConnectorClientWebSocketConfigOperation {

    static final String WEBSOCKET_ENDPOINT = "/connector/v1/entrypoint/websocket/endpoint";

    final CreateQuarkusHeaderProtocolOperation createQuarkusHeaderProtocolOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public WebSocketConfig execute(final Long clientId) {
        final var connectorUri = getServiceConfigOperation.getServiceConfig().client().connectorUri();
        final var connectorUrl = UriBuilder.fromUri(connectorUri)
                .path(WEBSOCKET_ENDPOINT)
                .build();

        final var wsToken = issueJwtTokenOperation.issueConnectorClientWsToken(clientId);
        final var secWebsocketProtocol = createQuarkusHeaderProtocolOperation.execute(wsToken);

        final var webSocketConfig = new WebSocketConfig(connectorUrl,
                secWebsocketProtocol);

        return webSocketConfig;
    }
}
