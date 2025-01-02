package com.omgservers.dispatcher.service.router.impl.method.transferServerBinaryMessage;

import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferServerBinaryMessageResponse;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferServerBinaryMessageMethodImpl implements TransferServerBinaryMessageMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<TransferServerBinaryMessageResponse> execute(
            final TransferServerBinaryMessageRequest request) {
        final var serverConnection = request.getServerConnection();
        final var buffer = request.getBuffer();

        final var clientConnection = routedConnections.getClientConnection(serverConnection);
        if (Objects.isNull(clientConnection)) {
            log.warn("Client connection was not found to transfer binary message, " +
                    "serverConnection={}", serverConnection);
            return Uni.createFrom().item(new TransferServerBinaryMessageResponse(Boolean.FALSE));
        }

        return clientConnection.sendBinary(buffer)
                .replaceWith(new TransferServerBinaryMessageResponse(Boolean.TRUE));
    }
}
