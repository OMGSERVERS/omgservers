package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.component;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class DispatcherRoom {

    @Getter
    final DispatcherConnection runtimeConnection;

    @Getter
    final Long runtimeId;

    final Map<Long, DispatcherConnection> indexBySubject;
    final Set<DispatcherConnection> playerConnections;

    public DispatcherRoom(final DispatcherConnection runtimeConnection) {
        this.runtimeConnection = runtimeConnection;
        this.runtimeId = runtimeConnection.getRuntimeId();

        playerConnections = new HashSet<>();
        indexBySubject = new HashMap<>();
    }

    public synchronized DispatcherConnection putIfAbsent(final DispatcherConnection playerConnection) {
        final var subject = playerConnection.getSubject();
        final var previousValue = indexBySubject.putIfAbsent(subject, playerConnection);
        if (Objects.isNull(previousValue)) {
            playerConnections.add(playerConnection);
        }

        return previousValue;
    }

    public synchronized boolean contains(final DispatcherConnection playerConnection) {
        return playerConnections.contains(playerConnection);
    }

    public synchronized boolean remove(final DispatcherConnection playerConnection) {
        final var removed = playerConnections.remove(playerConnection);
        if (removed) {
            final var subject = playerConnection.getSubject();
            indexBySubject.remove(subject);
        }

        return removed;
    }

    public synchronized List<DispatcherConnection> filterPlayerConnections(final List<Long> clients) {
        return clients.stream()
                .map(indexBySubject::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public synchronized List<DispatcherConnection> getAllPlayerConnections() {
        return playerConnections.stream().toList();
    }
}
