package com.omgservers.service.module.dispatcher.impl.service.webService.impl.webSocket;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.module.dispatcher.impl.service.webService.WebService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.WEBSOCKET})
@WebSocket(path = "/omgservers/v1/module/dispatcher/connection")
public class DispatcherEndpoint {

    final WebService webService;

    @OnOpen
    @WithSpan
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        return webService.onOpen(webSocketConnection);
    }

    @OnClose
    @WithSpan
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection,
                             final CloseReason closeReason) {
        return webService.onClose(webSocketConnection, closeReason);
    }

    @OnError
    @WithSpan
    public Uni<Void> onError(final WebSocketConnection webSocketConnection, final Throwable throwable) {
        return webService.onError(webSocketConnection, throwable);
    }

    @WithSpan
    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        return webService.onTextMessage(webSocketConnection, message);
    }

    @WithSpan
    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer buffer) {
        return webService.onBinaryMessage(webSocketConnection, buffer);
    }
}
