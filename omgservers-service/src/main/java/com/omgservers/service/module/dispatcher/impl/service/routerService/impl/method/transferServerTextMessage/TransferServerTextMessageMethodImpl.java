package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferServerTextMessageMethodImpl implements TransferServerTextMessageMethod {

    final RoutedConnections routedConnections;

    @Override
    public Uni<TransferServerTextMessageResponse> transferServerTextMessage(
            final TransferServerTextMessageRequest request) {
        final var serverConnection = request.getServerConnection();
        final var message = request.getMessage();

        final var clientConnection = routedConnections.getClientConnection(serverConnection);

        if (Objects.isNull(clientConnection)) {
            log.warn("Client connection was not found to transfer text message, serverConnection={}", serverConnection);
            return Uni.createFrom().item(new TransferServerTextMessageResponse(Boolean.FALSE));
        }

        return clientConnection.sendText(message)
                .replaceWith(new TransferServerTextMessageResponse(Boolean.TRUE));
    }
}
