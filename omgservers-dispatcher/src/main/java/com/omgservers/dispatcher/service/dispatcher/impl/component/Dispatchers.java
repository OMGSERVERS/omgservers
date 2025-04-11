package com.omgservers.dispatcher.service.dispatcher.impl.component;

import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class Dispatchers {

    final Map<Long, Dispatcher> indexByRuntimeId;

    public Dispatchers() {
        indexByRuntimeId = new HashMap<>();
    }

    public synchronized Dispatcher putIfAbsent(final Dispatcher dispatcher) {
        final var runtimeId = dispatcher.getRuntimeId();
        return indexByRuntimeId.putIfAbsent(runtimeId, dispatcher);
    }

    public synchronized Dispatcher getDispatcher(final Long runtimeId) {
        return indexByRuntimeId.get(runtimeId);
    }

    public synchronized Dispatcher deleteDispatcher(final Long runtimeId) {
        return indexByRuntimeId.remove(runtimeId);
    }

    public synchronized Dispatcher findDispatcher(final DispatcherConnection dispatcherConnection) {
        return indexByRuntimeId.values().stream()
                .filter(dispatcher -> {
                    final var runtime = dispatcher.getRuntimeConnection().equals(dispatcherConnection);
                    final var player = dispatcher.contains(dispatcherConnection);

                    return runtime || player;
                })
                .findFirst()
                .orElse(null);
    }
}
