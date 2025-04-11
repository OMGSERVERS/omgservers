package com.omgservers.dispatcher.service.dispatcher.impl.component;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@ToString
public class Dispatcher {

    @Getter
    @ToString.Exclude
    final DispatcherConnection runtimeConnection;

    @Getter
    final Long runtimeId;

    @ToString.Exclude
    final Map<Long, DispatcherConnection> indexBySubject;

    @ToString.Exclude
    final Set<DispatcherConnection> playerConnections;

    public Dispatcher(final DispatcherConnection runtimeConnection) {
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
