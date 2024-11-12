package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection;

import com.omgservers.service.component.ServiceTokenFactory;
import com.omgservers.service.module.dispatcher.component.DispatcherHeadersEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnector;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RouteServerConnectionMethodImpl implements RouteServerConnectionMethod {

    final WebSocketConnector<DispatcherModuleClient> webSocketConnector;
    final ServiceTokenFactory serviceTokenFactory;
    final RoutedConnections routedConnections;

    @Override
    public Uni<RouteServerConnectionResponse> execute(final RouteServerConnectionRequest request) {
        log.debug("Requested, {}", request);

        final var serverConnection = request.getServerConnection();
        final var serverUri = request.getServerUri();

        return createClientWebSocket(serverConnection, serverUri)
                .invoke(clientConnection -> {
                    log.info("Server connection was routed, serverConnection={}, serverUri={}, clientConnectionId={}",
                            serverConnection, request.getServerUri(), clientConnection.id());
                    routedConnections.put(serverConnection, clientConnection);
                })
                .replaceWith(new RouteServerConnectionResponse(Boolean.TRUE));
    }

    // WebSocketConnector is not thread safe
    synchronized Uni<WebSocketClientConnection> createClientWebSocket(final DispatcherConnection serverConnection,
                                                                      final URI serverUri) {
        final var runtimeId = serverConnection.getRuntimeId();
        final var userRole = serverConnection.getUserRole();
        final var subject = serverConnection.getSubject();

        final var serviceJwtToken = serviceTokenFactory.getServiceJwtToken();

        return webSocketConnector
                .baseUri(serverUri)
                .addHeader(DispatcherHeadersEnum.RUNTIME_ID.getHeaderName(), runtimeId.toString())
                .addHeader(DispatcherHeadersEnum.USER_ROLE.getHeaderName(), userRole.toString())
                .addHeader(DispatcherHeadersEnum.SUBJECT.getHeaderName(), subject.toString())
                .addHeader("Authorization", "Bearer " + serviceJwtToken)
                .connect();
    }
}
