package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.connector.ConnectorService;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageRequest;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageResponse;
import com.omgservers.connector.server.handler.component.ConnectorConnection;
import com.omgservers.connector.server.handler.component.ConnectorConnections;
import com.omgservers.connector.server.handler.dto.HandleTextMessageRequest;
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

    final ConnectorService connectorService;

    final ConnectorConnections connectorConnections;

    @Override
    public Uni<Void> execute(final HandleTextMessageRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var connectorConnection = connectorConnections.get(webSocketConnection);
        if (Objects.nonNull(connectorConnection)) {
            return receiveTextMessage(connectorConnection, message)
                    .replaceWithVoid();
        } else {
            log.error("Closing websocket \"{}\", connector connection not found", webSocketConnection.id());
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR);
        }
    }

    Uni<Boolean> receiveTextMessage(final ConnectorConnection connectorConnection,
                                    final String message) {
        final var request = new ReceiveTextMessageRequest(connectorConnection, message);
        return connectorService.execute(request)
                .map(ReceiveTextMessageResponse::getReceived);
    }
}
