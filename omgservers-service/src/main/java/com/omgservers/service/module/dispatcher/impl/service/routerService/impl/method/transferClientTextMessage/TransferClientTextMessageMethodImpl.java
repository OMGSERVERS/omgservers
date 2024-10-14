package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.transferClientTextMessage;

import com.omgservers.service.module.dispatcher.impl.service.routerService.component.RouterConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferClientTextMessageMethodImpl implements TransferClientTextMessageMethod {

    final RouterConnectionsContainer routerConnectionsContainer;

    @Override
    public Uni<TransferClientTextMessageResponse> transferClientTextMessage(
            final TransferClientTextMessageRequest request) {
        final var clientConnection = request.getClientConnection();
        final var message = request.getMessage();
        final var serverConnection = routerConnectionsContainer.getServerConnection(clientConnection);
        return serverConnection.sendText(message)
                .replaceWith(new TransferClientTextMessageResponse());
    }
}
