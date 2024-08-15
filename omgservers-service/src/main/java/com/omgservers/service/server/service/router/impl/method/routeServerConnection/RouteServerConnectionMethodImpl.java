package com.omgservers.service.server.service.router.impl.method.routeServerConnection;

import com.omgservers.service.server.security.ServiceSecurityAttributes;
import com.omgservers.service.server.service.router.component.RouterConnectionsContainer;
import com.omgservers.service.server.service.router.component.RouterWebSocketClient;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionResponse;
import io.quarkus.websockets.next.WebSocketConnector;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RouteServerConnectionMethodImpl implements RouteServerConnectionMethod {

    final WebSocketConnector<RouterWebSocketClient> webSocketConnector;
    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<RouteServerConnectionResponse> routeServerConnection(final RouteServerConnectionRequest request) {
        final var serverConnection = request.getServerConnection();
        final var serverUri = request.getServerUri();

        log.info("Route server connection, id={}, serverUri={}",
                serverConnection.id(), serverUri);

        final var securityIdentity = request.getSecurityIdentity();
        final var rawToken = securityIdentity
                .<String>getAttribute(ServiceSecurityAttributes.RAW_TOKEN.getAttributeName());
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributes.RUNTIME_ID.getAttributeName());

        return webSocketConnector
                .baseUri(serverUri)
                .addHeader("Authorization", "Bearer " + rawToken)
                .addHeader("Runtime-Id", runtimeId.toString())
                .connect()
                .invoke(clientConnection -> {
                    log.info("Connection was routed, id={}, serverUri={}",
                            serverConnection.id(), serverUri);
                    routerConnectionsContainer.put(serverConnection, clientConnection);
                })
                .replaceWith(new RouteServerConnectionResponse());
    }
}
