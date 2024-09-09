package com.omgservers.service.service.router.impl.method.closeServerConnection;

import com.omgservers.service.service.router.component.RouterConnectionsContainer;
import com.omgservers.service.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.service.router.dto.CloseServerConnectionResponse;
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
        log.debug("Close server connection, request={}", request);

        final var clientConnection = request.getClientConnection();
        final var closeReason = request.getCloseReason();

        final var serverConnection = routerConnectionsContainer.remove(clientConnection);
        return serverConnection.close(closeReason)
                .replaceWith(new CloseServerConnectionResponse());
    }
}