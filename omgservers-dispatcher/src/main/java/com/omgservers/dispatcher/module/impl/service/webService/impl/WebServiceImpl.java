package com.omgservers.dispatcher.module.impl.service.webService.impl;

import com.omgservers.dispatcher.module.impl.dto.OnBinaryMessageRequest;
import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
import com.omgservers.dispatcher.module.impl.dto.OnErrorRequest;
import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
import com.omgservers.dispatcher.module.impl.service.dispatcherService.DispatcherService;
import com.omgservers.dispatcher.module.impl.service.webService.WebService;
import com.omgservers.dispatcher.operation.GetDispatcherHeaderOperation;
import io.quarkus.security.identity.SecurityIdentity;
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
class WebServiceImpl implements WebService {

    final GetDispatcherHeaderOperation getDispatcherHeaderOperation;

    final DispatcherService dispatcherService;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<Void> onOpen(final WebSocketConnection webSocketConnection) {
        final var runtimeId = getDispatcherHeaderOperation.getRuntimeId(webSocketConnection);
        final var userRole = getDispatcherHeaderOperation.getUserRole(webSocketConnection);
        final var subject = getDispatcherHeaderOperation.getSubject(webSocketConnection);

        final var request = new OnOpenRequest(webSocketConnection, runtimeId, userRole, subject);
        return dispatcherService.execute(request);
    }

    @Override
    public Uni<Void> onClose(final WebSocketConnection webSocketConnection,
                             final CloseReason closeReason) {
        final var request = new OnCloseRequest(webSocketConnection, closeReason);
        return dispatcherService.execute(request);
    }

    @Override
    public Uni<Void> onError(final WebSocketConnection webSocketConnection,
                             final Throwable throwable) {
        final var request = new OnErrorRequest(webSocketConnection, throwable);
        return dispatcherService.execute(request);
    }

    @Override
    public Uni<Void> onTextMessage(final WebSocketConnection webSocketConnection,
                                   final String message) {
        final var request = new OnTextMessageRequest(webSocketConnection, message);
        return dispatcherService.execute(request);
    }

    @Override
    public Uni<Void> onBinaryMessage(final WebSocketConnection webSocketConnection,
                                     final Buffer buffer) {
        final var request = new OnBinaryMessageRequest(webSocketConnection, buffer);
        return dispatcherService.execute(request);
    }
}
