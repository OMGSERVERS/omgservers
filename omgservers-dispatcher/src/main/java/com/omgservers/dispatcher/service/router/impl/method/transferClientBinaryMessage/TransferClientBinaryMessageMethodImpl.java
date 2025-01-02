package com.omgservers.dispatcher.service.router.impl.method.transferClientBinaryMessage;

import com.omgservers.dispatcher.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferClientBinaryMessageResponse;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferClientBinaryMessageMethodImpl implements TransferClientBinaryMessageMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<TransferClientBinaryMessageResponse> execute(
            final TransferClientBinaryMessageRequest request) {
        final var clientConnection = request.getClientConnection();
        final var buffer = request.getBuffer();

        final var serverConnection = routedConnections.getServerConnection(clientConnection);

        if (Objects.isNull(serverConnection)) {
            log.warn("Server connection was not found to transfer binary buffer, id={}", clientConnection);
            return Uni.createFrom().item(new TransferClientBinaryMessageResponse(Boolean.FALSE));
        }

        return serverConnection.sendBuffer(buffer)
                .replaceWith(new TransferClientBinaryMessageResponse(Boolean.TRUE));
    }
}
