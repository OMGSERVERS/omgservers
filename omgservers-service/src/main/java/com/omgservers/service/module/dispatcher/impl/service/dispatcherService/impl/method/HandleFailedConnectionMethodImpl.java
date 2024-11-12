package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.components.DispatcherConnections;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpClosedException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleFailedConnectionMethodImpl implements HandleFailedConnectionMethod {

    final DispatcherConnections dispatcherConnections;

    @Override
    public Uni<Void> execute(final HandleFailedConnectionRequest request) {
        log.debug("Requested, {}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var t = request.getThrowable();

        final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
        if (Objects.nonNull(dispatcherConnection)) {
            final var runtimeId = dispatcherConnection.getRuntimeId();
            final var userRole = dispatcherConnection.getUserRole();
            final var subject = dispatcherConnection.getSubject();
            log.warn("Dispatcher connection failed, id={}, runtimeId={}, userRole={}, subject={}, {}:{}",
                    webSocketConnection.id(),
                    runtimeId,
                    userRole,
                    subject,
                    t.getClass().getSimpleName(),
                    t.getMessage());
        } else {
            if (!t.getClass().equals(HttpClosedException.class)) {
                log.error("WebSocket connection failed, and the corresponding dispatcher connection was not found, " +
                        "id={}, {}:{}", webSocketConnection.id(), t.getClass().getSimpleName(), t.getMessage());
            }
        }

        return Uni.createFrom().voidItem();
    }
}
