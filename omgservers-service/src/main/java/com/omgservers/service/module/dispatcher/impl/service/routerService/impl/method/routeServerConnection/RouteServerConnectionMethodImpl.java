package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterDispatcherClient;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.websockets.next.WebSocketConnector;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RouteServerConnectionMethodImpl implements RouteServerConnectionMethod {

    final WebSocketConnector<RouterDispatcherClient> webSocketConnector;
    final RouterConnectionsContainer routerConnectionsContainer;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<RouteServerConnectionResponse> routeServerConnection(final RouteServerConnectionRequest request) {
        final var serverConnection = request.getServerConnection();
        final var serverUri = request.getServerUri();

        log.info("Route server connection, id={}, serverUri={}",
                serverConnection.id(), serverUri);

        final var rawToken = securityIdentity
                .<String>getAttribute(ServiceSecurityAttributesEnum.RAW_TOKEN.getAttributeName());
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());

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
