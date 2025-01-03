package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.CloseServerConnectionResponse;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
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
    public Uni<CloseServerConnectionResponse> execute(final CloseServerConnectionRequest request) {
        log.trace("{}", request);

        final var clientConnection = request.getClientConnection();
        final var closeReason = request.getCloseReason();

        final var serverConnection = routedConnections.removeClientConnection(clientConnection);

        if (Objects.nonNull(serverConnection)) {
            final var webSocketConnection = serverConnection.getWebSocketConnection();
            if (webSocketConnection.isOpen()) {
                log.info("Closing server websocket \"{}\", reason=\"{}\"", clientConnection.id(), closeReason);
                return webSocketConnection.close(closeReason)
                        .replaceWith(new CloseServerConnectionResponse(Boolean.TRUE));
            }
        }

        return Uni.createFrom().item(new CloseServerConnectionResponse(Boolean.FALSE));
    }
}
