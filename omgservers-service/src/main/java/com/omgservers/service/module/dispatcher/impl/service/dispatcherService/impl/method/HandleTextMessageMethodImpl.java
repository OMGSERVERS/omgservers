package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnectionsContainer;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.RoomService;
import com.omgservers.service.module.dispatcher.impl.service.routerService.RouterService;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferServerTextMessageRequest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final DispatcherModule dispatcherModule;

    final DispatcherConnectionsContainer dispatcherConnectionsContainer;

    @Override
    public Uni<HandleTextMessageResponse> execute(final HandleTextMessageRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        final var webSocketType = dispatcherConnectionsContainer.getType(webSocketConnection);
        if (webSocketType.isPresent()) {
            return switch (webSocketType.get()) {
                case ROUTED -> routeTextMessage(webSocketConnection, message);
                case SERVER -> handleTextMessage(webSocketConnection, message);
            };
        } else {
            return webSocketConnection.close(CloseReason.INTERNAL_SERVER_ERROR)
                    .replaceWith(new HandleTextMessageResponse());
        }
    }

    Uni<HandleTextMessageResponse> routeTextMessage(final WebSocketConnection webSocketConnection,
                                                    final String message) {
        final var request = new TransferServerTextMessageRequest(webSocketConnection, message);
        return dispatcherModule.getRouterService().transferServerTextMessage(request)
                .replaceWith(new HandleTextMessageResponse());
    }

    Uni<HandleTextMessageResponse> handleTextMessage(final WebSocketConnection webSocketConnection,
                                                     final String message) {
        final var request = new com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleTextMessageRequest(webSocketConnection, message);
        return dispatcherModule.getRoomService().handleTextMessage(request)
                .replaceWith(new HandleTextMessageResponse());
    }
}
