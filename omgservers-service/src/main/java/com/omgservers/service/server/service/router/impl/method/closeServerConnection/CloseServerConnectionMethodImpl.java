package com.omgservers.service.server.service.router.impl.method.closeServerConnection;

import com.omgservers.service.server.service.router.component.RouterConnectionsContainer;
import com.omgservers.service.server.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.CloseServerConnectionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseServerConnectionMethodImpl implements CloseServerConnectionMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<CloseServerConnectionResponse> closeServerConnection(CloseServerConnectionRequest request) {
        final var clientConnection = request.getClientConnection();
        final var closeReason = request.getCloseReason();

        log.info("Client connection is closed, id={}, reason={}:{}", clientConnection.id(),
                closeReason.getCode(), closeReason.getMessage());
        final var serverConnection = routerConnectionsContainer.remove(clientConnection);
        return serverConnection.close(closeReason)
                .replaceWith(new CloseServerConnectionResponse());
    }
}
