package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeClientConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseClientConnectionMethodImpl implements CloseClientConnectionMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<CloseClientConnectionResponse> execute(final CloseClientConnectionRequest request) {
        log.debug("Close client connection, request={}", request);

        final var serverConnection = request.getServerConnection();
        final var closeReason = request.getCloseReason();

        final var clientConnection = routedConnections
                .removeServerConnection(serverConnection);

        if (clientConnection.isOpen()) {
            return clientConnection.close(closeReason)
                    .replaceWith(new CloseClientConnectionResponse(Boolean.TRUE));
        } else {
            return Uni.createFrom().item(new CloseClientConnectionResponse(Boolean.FALSE));
        }
    }
}
