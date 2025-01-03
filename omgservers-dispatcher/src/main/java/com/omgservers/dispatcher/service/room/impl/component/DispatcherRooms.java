package com.omgservers.dispatcher.service.room.impl.component;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class DispatcherRooms {

    final Map<Long, DispatcherRoom> indexByRuntimeId;

    public DispatcherRooms() {
        indexByRuntimeId = new HashMap<>();
    }

    public synchronized DispatcherRoom putIfAbsent(final DispatcherRoom dispatcherRoom) {
        final var runtimeId = dispatcherRoom.getRuntimeId();
        return indexByRuntimeId.putIfAbsent(runtimeId, dispatcherRoom);
    }

    public synchronized DispatcherRoom getRoom(final Long runtimeId) {
        return indexByRuntimeId.get(runtimeId);
    }

    public synchronized DispatcherRoom removeRoom(final Long runtimeId) {
        return indexByRuntimeId.remove(runtimeId);
    }

    public synchronized DispatcherRoom findRuntimeRoom(final DispatcherConnection runtimeConnection) {
        // TODO: Optimize this
        return indexByRuntimeId.values().stream()
                .filter(dispatcherRoom -> dispatcherRoom.getRuntimeConnection().equals(runtimeConnection))
                .findFirst()
                .orElse(null);
    }

    public synchronized DispatcherRoom findPlayerRoom(final DispatcherConnection playerConnection) {
        // TODO: Optimize this
        return indexByRuntimeId.values().stream()
                .filter(dispatcherRoom -> dispatcherRoom.contains(playerConnection))
                .findFirst()
                .orElse(null);
    }
}
