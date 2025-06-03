package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.dispatcher.DispatcherService;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferTextMessageResponse;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.server.handler.dto.HandleTextMessageRequest;
import com.omgservers.dispatcher.server.handler.impl.components.DispatcherConnections;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final DispatcherService dispatcherService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleTextMessageRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return transferTextMessage(dispatcherConnection, message)
                    .replaceWithVoid();
        } else {
            log.error("Closing websocket \"{}\", dispatcher connection not found", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferTextMessage(final DispatcherConnection dispatcherConnection,
                                     final String message) {
        final var request = new TransferTextMessageRequest(dispatcherConnection, message);
        return dispatcherService.execute(request)
                .map(TransferTextMessageResponse::getTransferred);
    }
}
