package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class DispatcherRooms {

    final Map<Long, DispatcherRoom> rooms;

    public DispatcherRooms() {
        rooms = new HashMap<>();
    }

    public synchronized DispatcherRoom putIfAbsent(final DispatcherRoom dispatcherRoom) {
        final var runtimeId = dispatcherRoom.getRuntimeId();
        return rooms.putIfAbsent(runtimeId, dispatcherRoom);
    }

    public synchronized DispatcherRoom getRoom(final Long runtimeId) {
        return rooms.get(runtimeId);
    }

    public synchronized DispatcherRoom removeRoom(final Long runtimeId) {
        return rooms.remove(runtimeId);
    }

    public synchronized DispatcherRoom findRuntimeRoom(final DispatcherConnection runtimeConnection) {
        // TODO: Optimize this
        return rooms.values().stream()
                .filter(dispatcherRoom -> dispatcherRoom.getRuntimeConnection().equals(runtimeConnection))
                .findFirst()
                .orElse(null);
    }

    public synchronized DispatcherRoom findPlayerRoom(final DispatcherConnection playerConnection) {
        // TODO: Optimize this
        return rooms.values().stream()
                .filter(dispatcherRoom -> dispatcherRoom.contains(playerConnection))
                .findFirst()
                .orElse(null);
    }
}
