package com.omgservers.service.service.dispatcher.impl.component;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class RoomsContainer {

    final Map<Long, RoomInstance> rooms;

    public RoomsContainer() {
        rooms = new HashMap<>();
    }

    public synchronized Optional<RoomInstance> replaceRoom(final RoomInstance roomInstance) {
        final var runtimeId = roomInstance.getRuntimeId();
        return Optional.ofNullable(rooms.put(runtimeId, roomInstance));
    }

    public synchronized Optional<RoomInstance> removeRoom(final Long runtimeId) {
        return Optional.ofNullable(rooms.remove(runtimeId));
    }

    public synchronized Optional<RoomInstance> getRoom(final Long runtimeId) {
        return Optional.ofNullable(rooms.get(runtimeId));
    }

    public synchronized Optional<RoomInstance> findRoom(final WebSocketConnection webSocketConnection) {
        // TODO: improve it
        return rooms.values().stream()
                .filter(roomInstance -> roomInstance.contains(webSocketConnection))
                .findFirst();
    }
}
