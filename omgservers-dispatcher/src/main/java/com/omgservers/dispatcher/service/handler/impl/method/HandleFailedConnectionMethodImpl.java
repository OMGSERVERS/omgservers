package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleFailedConnectionRequest;
import com.omgservers.dispatcher.service.handler.impl.components.DispatcherConnections;
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
        log.trace("{}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var t = request.getThrowable();

        if (!t.getClass().equals(HttpClosedException.class)) {
            final var dispatcherConnection = dispatcherConnections.get(webSocketConnection);
            if (Objects.nonNull(dispatcherConnection)) {
                log.warn("Websocket \"{}\" failed, {}, {}:{}",
                        webSocketConnection.id(),
                        dispatcherConnection,
                        t.getClass().getSimpleName(),
                        t.getMessage());
            } else {
                log.error("Websocket \"{}\" failed, {}:{}",
                        webSocketConnection.id(),
                        t.getClass().getSimpleName(),
                        t.getMessage());
            }
        }

        return Uni.createFrom().voidItem();
    }
}
