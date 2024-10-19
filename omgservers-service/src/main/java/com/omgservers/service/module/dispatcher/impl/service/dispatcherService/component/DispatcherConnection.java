package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component;

import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@ToString
public class DispatcherConnection {

    @Getter
    @ToString.Exclude
    final WebSocketConnection webSocketConnection;

    @Getter
    final ConnectionTypeEnum connectionType;

    @Getter
    final Long runtimeId;

    @Getter
    final UserRoleEnum userRole;

    @Getter
    final Long subject;

    final AtomicReference<Instant> lastUsage;

    public DispatcherConnection(final WebSocketConnection webSocketConnection,
                                final ConnectionTypeEnum connectionType,
                                final Long runtimeId,
                                final UserRoleEnum userRole,
                                final Long subject) {
        this.webSocketConnection = webSocketConnection;
        this.connectionType = connectionType;
        this.runtimeId = runtimeId;
        this.userRole = userRole;
        this.subject = subject;

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

    public Uni<Void> sendBytes(final byte[] bytes) {
        return webSocketConnection.sendBinary(bytes)
                .invoke(voidItem -> lastUsage.set(Instant.now()));
    }

    public Uni<Void> sendBuffer(final Buffer buffer) {
        return webSocketConnection.sendBinary(buffer)
                .invoke(voidItem -> lastUsage.set(Instant.now()));
    }

    public Instant getLastUsage() {
        return lastUsage.get();
    }
}