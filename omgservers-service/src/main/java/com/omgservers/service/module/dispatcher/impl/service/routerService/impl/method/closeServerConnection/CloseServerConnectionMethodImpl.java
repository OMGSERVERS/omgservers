package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeServerConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseServerConnectionMethodImpl implements CloseServerConnectionMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<CloseServerConnectionResponse> closeServerConnection(final CloseServerConnectionRequest request) {
        log.trace("Requested, {}", request);

        final var clientConnection = request.getClientConnection();
        final var closeReason = request.getCloseReason();

        final var serverConnection = routedConnections.removeClientConnection(clientConnection);

        if (Objects.nonNull(serverConnection)) {
            final var webSocketConnection = serverConnection.getWebSocketConnection();
            if (webSocketConnection.isOpen()) {
                return webSocketConnection.close(closeReason)
                        .replaceWith(new CloseServerConnectionResponse(Boolean.TRUE));
            }
        }

        return Uni.createFrom().item(new CloseServerConnectionResponse(Boolean.FALSE));
    }
}
