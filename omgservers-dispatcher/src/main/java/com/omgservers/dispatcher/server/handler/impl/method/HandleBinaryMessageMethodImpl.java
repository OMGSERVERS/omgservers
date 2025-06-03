package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.dispatcher.DispatcherService;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.TransferBinaryMessageResponse;
import com.omgservers.dispatcher.server.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.server.handler.dto.HandleBinaryMessageRequest;
import com.omgservers.dispatcher.server.handler.impl.components.DispatcherConnections;
import io.quarkus.websockets.next.CloseReason;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final DispatcherService dispatcherService;

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleBinaryMessageRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            return transferBinaryMessage(dispatcherConnection, buffer)
                    .replaceWithVoid();
        } else {
            log.error("Closing websocket \"{}\", dispatcher connection not found", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> transferBinaryMessage(final DispatcherConnection dispatcherConnection,
                                       final Buffer buffer) {
        final var request = new TransferBinaryMessageRequest(dispatcherConnection, buffer);
        return dispatcherService.execute(request)
                .map(TransferBinaryMessageResponse::getTransferred);
    }
}
