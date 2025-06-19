package com.omgservers.service.operation.client;

import com.omgservers.schema.model.user.UserRoleEnum;
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
class CreateDispatcherClientWebSocketConfigOperationImpl implements CreateDispatcherClientWebSocketConfigOperation {

    static final String WEBSOCKET_ENDPOINT = "/dispatcher/v1/entrypoint/websocket/endpoint";

    final CreateQuarkusHeaderProtocolOperation createQuarkusHeaderProtocolOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public WebSocketConfig execute(final Long clientId, final Long runtimeId) {
        final var dispatcherUri = getServiceConfigOperation.getServiceConfig().client().dispatcherUri();
        final var dispatcherUrl = UriBuilder.fromUri(dispatcherUri)
                .path(WEBSOCKET_ENDPOINT)
                .build();

        final var wsToken = issueJwtTokenOperation.issueDispatcherClientWsToken(clientId,
                runtimeId,
                UserRoleEnum.PLAYER);
        final var secWebsocketProtocol = createQuarkusHeaderProtocolOperation.execute(wsToken);

        final var webSocketConfig = new WebSocketConfig(dispatcherUrl,
                secWebsocketProtocol);

        return webSocketConfig;
    }
}
