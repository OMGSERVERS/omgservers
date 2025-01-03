package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionResponse;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseClientConnectionMethodImpl implements CloseClientConnectionMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<CloseClientConnectionResponse> execute(final CloseClientConnectionRequest request) {
        log.trace("{}", request);

        final var serverConnection = request.getServerConnection();
        final var closeReason = request.getCloseReason();

        final var clientConnection = routedConnections
                .removeServerConnection(serverConnection);

        if (Objects.nonNull(clientConnection)) {
            if (clientConnection.isOpen()) {
                log.info("Closing client websocket \"{}\", reason=\"{}\"", clientConnection.id(), closeReason);
                return clientConnection.close(closeReason)
                        .replaceWith(new CloseClientConnectionResponse(Boolean.TRUE));
            }
        }

        return Uni.createFrom().item(new CloseClientConnectionResponse(Boolean.FALSE));
    }
}
