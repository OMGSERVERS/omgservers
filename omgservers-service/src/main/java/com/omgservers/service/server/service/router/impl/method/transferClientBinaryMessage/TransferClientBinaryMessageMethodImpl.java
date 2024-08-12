package com.omgservers.service.server.service.router.impl.method.transferClientBinaryMessage;

import com.omgservers.service.server.service.router.component.RouterConnectionsContainer;
import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.server.service.router.dto.TransferClientBinaryMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferClientBinaryMessageMethodImpl implements TransferClientBinaryMessageMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(
            final TransferClientBinaryMessageRequest request) {
        final var clientConnection = request.getClientConnection();
        final var message = request.getMessage();
        final var serverConnection = routerConnectionsContainer.getServerConnection(clientConnection);
        return serverConnection.sendBinary(message)
                .replaceWith(new TransferClientBinaryMessageResponse());
    }
}
