package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.configuration.ConnectorCloseReason;
import com.omgservers.connector.server.handler.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    @Override
    public Uni<Void> execute(final HandleBinaryMessageRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        log.error("Closing websocket \"{}\", binary message not supported", webSocketConnection.id());
        return webSocketConnection.close(ConnectorCloseReason.BINARY_UNSUPPORTED);
    }
}
