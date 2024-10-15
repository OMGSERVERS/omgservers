package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerBinaryMessageRequest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final DispatcherModule dispatcherModule;

    final DispatcherConnectionsContainer dispatcherConnectionsContainer;

    @Override
    public Uni<HandleBinaryMessageResponse> execute(
            final HandleBinaryMessageRequest request) {
        log.trace("Handle binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();

        final var webSocketType = dispatcherConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return switch (webSocketType.get()) {
                case ROUTED -> routeBinaryMessage(webSocketConnection, buffer);
                case SERVER -> handleBinaryMessage(webSocketConnection, buffer);
            };
        } else {
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR)
                    .replaceWith(new HandleBinaryMessageResponse());
        }
    }

    Uni<HandleBinaryMessageResponse> routeBinaryMessage(final WebSocketConnection webSocketConnection,
                                                        final Buffer buffer) {
        final var request = new TransferServerBinaryMessageRequest(webSocketConnection, buffer);
        return dispatcherModule.getRouterService().transferServerBinaryMessage(request)
                .replaceWith(new HandleBinaryMessageResponse());
    }

    Uni<HandleBinaryMessageResponse> handleBinaryMessage(final WebSocketConnection webSocketConnection,
                                                         final Buffer buffer) {
        final var request =
                new com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleBinaryMessageRequest(
                        webSocketConnection, buffer);
        return dispatcherModule.getRoomService().handleBinaryMessage(request)
                .replaceWith(new HandleBinaryMessageResponse());
    }
}
