package com.omgservers.service.service.room.impl.component;

import com.omgservers.service.service.room.impl.component.exception.TokenIsAlreadyUsedException;
import io.quarkus.websockets.next.WebSocketConnection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class RoomInstance {

    @Getter
    final Long runtimeId;

    final Map<WebSocketConnection, RoomConnection> connectionByWebsocket;
    final AtomicReference<RoomConnection> runtimeConnection;
    final Map<String, RoomConnection> connectionByTokenId;
    final Map<Long, RoomConnection> connectionByClientId;

    public RoomInstance(final Long runtimeId) {
        this.runtimeId = runtimeId;

        connectionByWebsocket = new HashMap<>();
        connectionByTokenId = new HashMap<>();
        runtimeConnection = new AtomicReference<>();
        connectionByClientId = new HashMap<>();
    }

    public synchronized Optional<RoomConnection> replaceConnection(final RoomConnection roomConnection) {
        try {
            addConnection(roomConnection);
            return Optional.empty();
        } catch (TokenIsAlreadyUsedException e) {
            final var previousConnection = getPreviousConnection(roomConnection);
            previousConnection.ifPresent(this::removeConnection);
            replaceConnection(roomConnection);
            return previousConnection;
        }
    }

    public synchronized void addConnection(final RoomConnection roomConnection)
            throws TokenIsAlreadyUsedException {
        if (!runtimeId.equals(roomConnection.getRuntimeId())) {
            log.error("Mismatched room and connection, skip operation, " +
                    "runtimeId={}, {}", runtimeId, roomConnection);
        }

        final var tokenId = roomConnection.getUsedTokenId();
        if (connectionByTokenId.containsKey(tokenId)) {
            throw new TokenIsAlreadyUsedException();
        }

        final var role = roomConnection.getRole();
        switch (role) {
            case WORKER -> runtimeConnection.set(roomConnection);
            case PLAYER -> {
                final var clientId = roomConnection.getSubject();
                connectionByClientId.put(clientId, roomConnection);
            }
            default -> {
                log.error("Unauthorized connection, skip operation, {}", roomConnection);
                return;
            }
        }

        connectionByTokenId.put(tokenId, roomConnection);

        final var webSocketConnection = roomConnection.getWebSocketConnection();
        connectionByWebsocket.put(webSocketConnection, roomConnection);
    }

    public synchronized Optional<RoomConnection> getConnection(final WebSocketConnection webSocketConnection) {
        return Optional.ofNullable(connectionByWebsocket.get(webSocketConnection));
    }

    public synchronized List<RoomConnection> getClientConnections(final List<Long> clients) {
        return clients.stream()
                .map(connectionByClientId::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public synchronized Optional<RoomConnection> getRuntimeConnection() {
        return Optional.ofNullable(runtimeConnection.get());
    }

    public synchronized Optional<RoomConnection> getPreviousConnection(final RoomConnection roomConnection) {
        final var tokenId = roomConnection.getUsedTokenId();
        final var previousConnection = connectionByTokenId.get(tokenId);
        return Optional.ofNullable(previousConnection);
    }

    public synchronized Optional<RoomConnection> removeConnection(final RoomConnection roomConnection) {
        final var webSocketConnection = roomConnection.getWebSocketConnection();
        return removeConnection(webSocketConnection);
    }

    public synchronized Optional<RoomConnection> removeConnection(final WebSocketConnection webSocketConnection) {
        final var removedConnection = connectionByWebsocket.remove(webSocketConnection);
        if (Objects.isNull(removedConnection)) {
            return Optional.empty();
        }

        final var tokenId = removedConnection.getUsedTokenId();
        connectionByTokenId.remove(tokenId);

        final var role = removedConnection.getRole();
        switch (role) {
            case WORKER -> runtimeConnection.set(null);
            case PLAYER -> {
                final var clientId = removedConnection.getSubject();
                connectionByClientId.remove(clientId);
            }
        }

        return Optional.of(removedConnection);
    }

    public synchronized List<RoomConnection> getAllConnections() {
        return connectionByWebsocket.values().stream().toList();
    }

    public synchronized boolean contains(final WebSocketConnection webSocketConnection) {
        return connectionByWebsocket.containsKey(webSocketConnection);
    }
}
