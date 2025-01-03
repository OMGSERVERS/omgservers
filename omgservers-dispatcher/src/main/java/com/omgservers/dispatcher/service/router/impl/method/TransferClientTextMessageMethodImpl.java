package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferClientTextMessageResponse;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
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
    public Uni<TransferClientTextMessageResponse> execute(
            final TransferClientTextMessageRequest request) {
        log.trace("{}", request);

        final var clientConnection = request.getClientConnection();
        final var message = request.getMessage();

        final var serverConnection = routedConnections.getServerConnection(clientConnection);

        if (Objects.isNull(serverConnection)) {
            log.error("Server websocket was not found for the client connection {}", clientConnection.id());
            return Uni.createFrom().item(new TransferClientTextMessageResponse(Boolean.FALSE));
        }

        return serverConnection.sendText(message)
                .replaceWith(new TransferClientTextMessageResponse(Boolean.TRUE));
    }
}
