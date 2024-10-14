package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferServerTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferServerTextMessageMethodImpl implements TransferServerTextMessageMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<TransferServerTextMessageResponse> transferServerTextMessage(
            final TransferServerTextMessageRequest request) {
        final var serverConnection = request.getServerConnection();
        final var message = request.getMessage();
        final var clientConnection = routerConnectionsContainer.getClientConnection(serverConnection);
        return clientConnection.sendText(message)
                .replaceWith(new TransferServerTextMessageResponse());
    }
}
