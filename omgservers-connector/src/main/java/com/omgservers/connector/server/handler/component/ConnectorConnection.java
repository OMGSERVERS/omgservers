package com.omgservers.connector.server.handler.component;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ToString
public class ConnectorConnection {

    @Getter
    @ToString.Exclude
    final WebSocketConnection webSocketConnection;

    @Getter
    final UserRoleEnum userRole;

    @Getter
    final Long clientId;

    final AtomicReference<Instant> lastUsage;

    public ConnectorConnection(final WebSocketConnection webSocketConnection,
                               final UserRoleEnum userRole,
                               final Long clientId) {
        this.webSocketConnection = webSocketConnection;
        this.userRole = userRole;
        this.clientId = clientId;

        lastUsage = new AtomicReference<>(Instant.now());
    }

    @ToString.Include(rank = 1)
    public String id() {
        return webSocketConnection.id();
    }

    public Uni<Void> sendText(final String message) {
        return webSocketConnection.sendText(message)
                .invoke(voidItem -> lastUsage.set(Instant.now()));
    }

    public Instant getLastUsage() {
        return lastUsage.get();
    }
}
