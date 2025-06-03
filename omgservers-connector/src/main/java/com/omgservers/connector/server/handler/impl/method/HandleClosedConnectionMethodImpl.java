package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.connector.ConnectorService;
import com.omgservers.connector.server.connector.dto.DeleteConnectorRequest;
import com.omgservers.connector.server.connector.dto.DeleteConnectorResponse;
import com.omgservers.connector.server.handler.component.ConnectorConnection;
import com.omgservers.connector.server.handler.component.ConnectorConnections;
import com.omgservers.connector.server.handler.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleClosedConnectionMethodImpl implements HandleClosedConnectionMethod {

    final ConnectorService connectorService;

    final ConnectorConnections connectorConnections;

    @Override
    public Uni<Void> execute(final HandleClosedConnectionRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var closeReason = request.getCloseReason();

        final var connectorConnection = connectorConnections.get(webSocketConnection);
        if (Objects.nonNull(connectorConnection)) {
            return handleConnectorConnection(connectorConnection)
                    .replaceWithVoid();
        } else {
            log.error("Connector connection was not found, skip operation for \"{}\"", webSocketConnection.id());
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> handleConnectorConnection(final ConnectorConnection connectorConnection) {
        final var request = new DeleteConnectorRequest(connectorConnection);
        return connectorService.execute(request)
                .map(DeleteConnectorResponse::getDeleted);
    }
}
