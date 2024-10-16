package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferClientTextMessageMethodImpl implements TransferClientTextMessageMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<TransferClientTextMessageResponse> transferClientTextMessage(
            final TransferClientTextMessageRequest request) {
        final var clientConnection = request.getClientConnection();
        final var message = request.getMessage();

        final var serverConnection = routedConnections.getServerConnection(clientConnection);

        if (Objects.isNull(serverConnection)) {
            log.warn("Server connection was not found to transfer text message, id={}", clientConnection.id());
            return Uni.createFrom().item(new TransferClientTextMessageResponse(Boolean.FALSE));
        }

        final var webSocketConnection = serverConnection.getWebSocketConnection();
        return webSocketConnection.sendText(message)
                .replaceWith(new TransferClientTextMessageResponse(Boolean.TRUE));
    }
}
