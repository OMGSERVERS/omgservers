package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.configuration.ConnectorCloseReason;
import com.omgservers.connector.server.connector.ConnectorService;
import com.omgservers.connector.server.connector.dto.CreateConnectorRequest;
import com.omgservers.connector.server.connector.dto.CreateConnectorResponse;
import com.omgservers.connector.server.handler.component.ConnectorConnection;
import com.omgservers.connector.server.handler.component.ConnectorConnections;
import com.omgservers.connector.server.handler.dto.HandleOpenedConnectionRequest;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleOpenedConnectionMethodImpl implements HandleOpenedConnectionMethod {

    final ConnectorService connectorService;

    final ConnectorConnections connectorConnections;

    @Override
    public Uni<Void> execute(final HandleOpenedConnectionRequest request) {
        log.debug("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var userRole = request.getUserRole();
        final var clientId = request.getClientId();

        log.info("Opening websocket \"{}\", userRole=\"{}\", clientId=\"{}\"",
                webSocketConnection.id(),
                userRole,
                clientId);

        final var connectorConnection = new ConnectorConnection(webSocketConnection,
                userRole,
                clientId);

        return handleConnectorConnection(connectorConnection)
                .flatMap(result -> {
                    if (!result) {
                        log.error("Closing websocket \"{}\", failed to open connection", webSocketConnection.id());
                        return webSocketConnection.close(ConnectorCloseReason.FAILED_TO_OPEN);
                    } else {
                        connectorConnections.put(connectorConnection);
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Boolean> handleConnectorConnection(final ConnectorConnection connectorConnection) {
        final var userRole = connectorConnection.getUserRole();

        if (userRole.equals(UserRoleEnum.PLAYER)) {
            final var createConnectorRequest = new CreateConnectorRequest(connectorConnection);
            return connectorService.execute(createConnectorRequest)
                    .map(CreateConnectorResponse::getCreated);
        } else {
            log.warn("Connection established using an incorrect user role \"{}\"", userRole);
            return Uni.createFrom().item(Boolean.FALSE);
        }
    }
}
