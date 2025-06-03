package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.user.UserRoleEnum;
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
class CreateRuntimeDispatcherConnectionUrlOperationImpl implements CreateRuntimeDispatcherConnectionUrlOperation {

    static final String DISPATCHER_ENDPOINT = "/dispatcher/v1/entrypoint/websocket/endpoint";
    static final String WS_TOKEN_PARAM = "ws_token";

    final GetServiceConfigOperation getServiceConfigOperation;
    final IssueJwtTokenOperation issueJwtTokenOperation;

    @Override
    public URI execute(final Long userId, final Long runtimeId) {
        final var dispatcherUri = getServiceConfigOperation.getServiceConfig().runtime().dispatcherUri();
        final var wsToken = issueJwtTokenOperation.issueDispatcherClientWsToken(userId, runtimeId, UserRoleEnum.RUNTIME);

        final var connectionUrl = UriBuilder.fromUri(dispatcherUri)
                .path(DISPATCHER_ENDPOINT)
                .queryParam(WS_TOKEN_PARAM, wsToken)
                .build();

        return connectionUrl;
    }
}
