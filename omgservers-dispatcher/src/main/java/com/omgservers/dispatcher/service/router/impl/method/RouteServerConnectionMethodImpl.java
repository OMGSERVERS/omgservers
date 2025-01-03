package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.component.DispatcherTokenContainer;
import com.omgservers.dispatcher.security.DispatcherHeadersEnum;
import com.omgservers.dispatcher.service.handler.component.DispatcherCloseReason;
import com.omgservers.dispatcher.service.handler.component.DispatcherConnection;
import com.omgservers.dispatcher.service.router.RouterService;
import com.omgservers.dispatcher.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionResponse;
import com.omgservers.dispatcher.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.dispatcher.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.dispatcher.service.router.impl.component.RoutedConnections;
import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RouteServerConnectionMethodImpl implements RouteServerConnectionMethod {

    final RouterService routerService;

    final DispatcherTokenContainer dispatcherTokenContainer;
    final RoutedConnections routedConnections;

    @Override
    public Uni<RouteServerConnectionResponse> execute(final RouteServerConnectionRequest request) {
        log.trace("{}", request);

        final var serverConnection = request.getServerConnection();
        final var serverUri = request.getServerUri();

        return createClientWebSocket(serverConnection, serverUri)
                .invoke(clientConnection -> {
                    log.info("Websocket \"{}\" was routed via \"{}\"", serverConnection.id(), clientConnection.id());
                    routedConnections.put(serverConnection, clientConnection);
                })
                .replaceWith(new RouteServerConnectionResponse(Boolean.TRUE));
    }

    Uni<WebSocketClientConnection> createClientWebSocket(final DispatcherConnection serverConnection,
                                                         final URI serverUri) {
        final var runtimeId = serverConnection.getRuntimeId();
        final var userRole = serverConnection.getUserRole();
        final var subject = serverConnection.getSubject();

        final var token = dispatcherTokenContainer.getToken();

        return BasicWebSocketConnector.create()
                .baseUri(serverUri)
                .path("/dispatcher/v1/module/dispatcher/endpoint")
                .addHeader(DispatcherHeadersEnum.RUNTIME_ID.getHeaderName(), runtimeId.toString())
                .addHeader(DispatcherHeadersEnum.USER_ROLE.getHeaderName(), userRole.toString())
                .addHeader(DispatcherHeadersEnum.SUBJECT.getHeaderName(), subject.toString())
                .addHeader("Authorization", "Bearer " + token)
                .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
                .onClose(this::closeServerConnection)
                .onTextMessage(this::handleTextMessage)
                .onBinaryMessage(this::handleBinaryMessage)
                .connect();
    }

    void closeServerConnection(final WebSocketClientConnection clientConnection,
                               final CloseReason closeReason) {
        final var request = new CloseServerConnectionRequest(clientConnection, closeReason);
        routerService.closeServerConnection(request)
                .subscribe().with(
                        response -> {
                            if (response.getClosed()) {
                                log.debug("Client connection \"{}\" was closed, closeReason=\"{}\"",
                                        clientConnection.id(), closeReason);
                            }
                        },
                        failure -> {
                            log.error("Failed to close server connection, {}:{}",
                                    failure.getClass().getSimpleName(), failure.getMessage());
                        });
    }

    void handleTextMessage(final WebSocketClientConnection clientConnection,
                           final String message) {
        final var request = new TransferClientTextMessageRequest(clientConnection, message);
        routerService.transferClientTextMessage(request)
                .subscribe().with(
                        response -> {
                        },
                        failure -> {
                            log.error("Failed to transfer text message, {}, {}:{}",
                                    request, failure.getClass().getSimpleName(), failure.getMessage());

                            closeServerConnection(clientConnection, DispatcherCloseReason.TRANSFER_FAILED);
                        });
    }

    void handleBinaryMessage(final WebSocketClientConnection clientConnection,
                             final Buffer buffer) {
        final var request = new TransferClientBinaryMessageRequest(clientConnection, buffer);
        routerService.transferClientBinaryMessage(request)
                .subscribe().with(
                        response -> {
                        },
                        failure -> {
                            log.error("Failed to transfer binary message, {}, {}:{}",
                                    request, failure.getClass().getSimpleName(), failure.getMessage());

                            closeServerConnection(clientConnection, DispatcherCloseReason.TRANSFER_FAILED);
                        });
    }
}
