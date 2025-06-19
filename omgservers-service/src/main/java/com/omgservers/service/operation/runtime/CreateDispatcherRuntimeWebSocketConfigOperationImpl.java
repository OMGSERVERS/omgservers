package com.omgservers.service.operation.runtime;

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
class CreateDispatcherRuntimeWebSocketConfigOperationImpl implements CreateDispatcherRuntimeWebSocketConfigOperation {

    static final String DISPATCHER_ENDPOINT = "/dispatcher/v1/entrypoint/websocket/endpoint";

    final CreateQuarkusHeaderProtocolOperation createQuarkusHeaderProtocolOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public WebSocketConfig execute(final Long userId, final Long runtimeId) {
        final var dispatcherUri = getServiceConfigOperation.getServiceConfig().runtime().dispatcherUri();
        final var dispatcherUrl = UriBuilder.fromUri(dispatcherUri)
                .path(DISPATCHER_ENDPOINT)
                .build();

        final var wsToken = issueJwtTokenOperation.issueDispatcherClientWsToken(userId,
                runtimeId,
                UserRoleEnum.RUNTIME);
        final var secWebsocketProtocol = createQuarkusHeaderProtocolOperation.execute(wsToken);

        final var webSocketConfig = new WebSocketConfig(dispatcherUrl,
                secWebsocketProtocol);

        return webSocketConfig;
    }
}
