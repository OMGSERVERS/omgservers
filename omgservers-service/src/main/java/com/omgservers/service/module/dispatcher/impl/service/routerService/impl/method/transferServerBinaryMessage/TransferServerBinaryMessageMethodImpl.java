package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerBinaryMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferServerBinaryMessageMethodImpl implements TransferServerBinaryMessageMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(
            final TransferServerBinaryMessageRequest request) {
        final var serverConnection = request.getServerConnection();
        final var buffer = request.getBuffer();
        final var clientConnection = routerConnectionsContainer.getClientConnection(serverConnection);
        return clientConnection.sendBinary(buffer)
                .replaceWith(new TransferServerBinaryMessageResponse());
    }
}