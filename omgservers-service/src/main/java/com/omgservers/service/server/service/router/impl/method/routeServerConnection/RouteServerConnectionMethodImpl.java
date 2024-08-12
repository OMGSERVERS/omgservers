package com.omgservers.service.server.service.router.impl.method.routeServerConnection;

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
        final var query = serverConnection.handshakeRequest().query();
        final var serverUri = request.getServerUri();

        log.info("Route server connection, id={}, serverUri={}, query={}",
                serverConnection.id(), serverConnection, query);

        return webSocketConnector.baseUri(serverUri).connect()
                .invoke(clientConnection -> routerConnectionsContainer.put(serverConnection, clientConnection))
                .replaceWith(new RouteServerConnectionResponse());
    }
}
