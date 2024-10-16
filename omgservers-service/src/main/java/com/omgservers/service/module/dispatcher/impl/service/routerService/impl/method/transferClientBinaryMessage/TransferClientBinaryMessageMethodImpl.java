package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientBinaryMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
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
    public Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(
            final TransferClientBinaryMessageRequest request) {
        final var clientConnection = request.getClientConnection();
        final var message = request.getMessage();

        final var serverConnection = routedConnections.getServerConnection(clientConnection);

        if (Objects.isNull(serverConnection)) {
            log.warn("Server connection was not found to transfer binary message, id={}", clientConnection);
            return Uni.createFrom().item(new TransferClientBinaryMessageResponse(Boolean.FALSE));
        }

        final var webSocketConnection = serverConnection.getWebSocketConnection();
        return webSocketConnection.sendBinary(message)
                .replaceWith(new TransferClientBinaryMessageResponse(Boolean.TRUE));
    }
}
