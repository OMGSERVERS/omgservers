package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnBinaryMessage;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocketClient;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@WebSocketClient(path = "/omgservers/v1/module/dispatcher/connection")
public class DispatcherModuleClient {

    final DispatcherModule dispatcherModule;

    @OnOpen
    public Uni<Void> onOpen(final WebSocketClientConnection clientConnection) {
        return Uni.createFrom().voidItem();
    }

    @OnClose
    public Uni<Void> onClose(final WebSocketClientConnection clientConnection,
                             final CloseReason closeReason) {
        final var request = new CloseServerConnectionRequest(clientConnection, closeReason);
        return dispatcherModule.getRouterService().closeServerConnection(request)
                .replaceWithVoid();
    }

    @OnTextMessage
    public Uni<Void> onTextMessage(final WebSocketClientConnection clientConnection,
                                   final String message) {
        final var request = new TransferClientTextMessageRequest(clientConnection, message);
        return dispatcherModule.getRouterService().transferClientTextMessage(request)
                .replaceWithVoid();
    }

    @OnBinaryMessage
    public Uni<Void> onBinaryMessage(final WebSocketClientConnection clientConnection,
                                     final Buffer buffer) {
        final var request = new TransferClientBinaryMessageRequest(clientConnection, buffer);
        return dispatcherModule.getRouterService().transferClientBinaryMessage(request)
                .replaceWithVoid();
    }
}
