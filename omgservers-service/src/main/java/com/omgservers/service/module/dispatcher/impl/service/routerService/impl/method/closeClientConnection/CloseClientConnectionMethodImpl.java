package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeClientConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseClientConnectionMethodImpl implements CloseClientConnectionMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<CloseClientConnectionResponse> closeClientConnection(final CloseClientConnectionRequest request) {
        log.debug("Close client connection, request={}", request);

        final var serverConnection = request.getServerConnection();
        final var closeReason = request.getCloseReason();

        final var clientConnection = routerConnectionsContainer.remove(serverConnection);
        return clientConnection.close(closeReason)
                .replaceWith(new CloseClientConnectionResponse());
    }
}
