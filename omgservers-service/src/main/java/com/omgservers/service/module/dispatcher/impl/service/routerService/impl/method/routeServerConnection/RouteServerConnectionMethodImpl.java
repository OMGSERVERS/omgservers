package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection;

import com.omgservers.service.component.ServiceTokenFactory;
import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.component.DispatcherHeadersEnum;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherCloseReason;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.component.DispatcherConnection;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.TransferClientTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.impl.component.RoutedConnections;
import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RouteServerConnectionMethodImpl implements RouteServerConnectionMethod {

    final ServiceTokenFactory serviceTokenFactory;
    final RoutedConnections routedConnections;
    final DispatcherModule dispatcherModule;

    @Override
    public Uni<RouteServerConnectionResponse> execute(final RouteServerConnectionRequest request) {
        log.debug("Requested, {}", request);

        final var serverConnection = request.getServerConnection();
        final var serverUri = request.getServerUri();

        return createClientWebSocket(serverConnection, serverUri)
                .invoke(clientConnection -> {
                    log.info("Server connection was routed, serverConnection={}, serverUri={}, clientConnectionId={}",
                            serverConnection, request.getServerUri(), clientConnection.id());
                    routedConnections.put(serverConnection, clientConnection);
                })
                .replaceWith(new RouteServerConnectionResponse(Boolean.TRUE));
    }

    Uni<WebSocketClientConnection> createClientWebSocket(final DispatcherConnection serverConnection,
                                                         final URI serverUri) {
        final var runtimeId = serverConnection.getRuntimeId();
        final var userRole = serverConnection.getUserRole();
        final var subject = serverConnection.getSubject();

        final var serviceJwtToken = serviceTokenFactory.getServiceJwtToken();

        return BasicWebSocketConnector.create()
                .baseUri(serverUri)
                .path("/omgservers/v1/module/dispatcher/connection")
                .addHeader(DispatcherHeadersEnum.RUNTIME_ID.getHeaderName(), runtimeId.toString())
                .addHeader(DispatcherHeadersEnum.USER_ROLE.getHeaderName(), userRole.toString())
                .addHeader(DispatcherHeadersEnum.SUBJECT.getHeaderName(), subject.toString())
                .addHeader("Authorization", "Bearer " + serviceJwtToken)
                .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
                .onClose(this::closeServerConnection)
                .onTextMessage(this::handleTextMessage)
                .onBinaryMessage(this::handleBinaryMessage)
                .connect();
    }

    void closeServerConnection(final WebSocketClientConnection clientConnection,
                               final CloseReason closeReason) {
        final var request = new CloseServerConnectionRequest(clientConnection, closeReason);
        dispatcherModule.getRouterService().closeServerConnection(request)
                .subscribe().with(
                        response -> {
                            if (response.getClosed()) {
                                log.info("Server connection was closed, closeReason={}", closeReason);
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
        dispatcherModule.getRouterService().transferClientTextMessage(request)
                .subscribe().with(
                        response -> {
                            if (response.getTransferred()) {
                                log.trace("Client text message was transferred, {}", message);
                            }
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
        dispatcherModule.getRouterService().transferClientBinaryMessage(request)
                .subscribe().with(
                        response -> {
                            if (response.getTransferred()) {
                                log.trace("Client binary message was transferred, {}", buffer);
                            }
                        },
                        failure -> {
                            log.error("Failed to transfer binary message, {}, {}:{}",
                                    request, failure.getClass().getSimpleName(), failure.getMessage());

                            closeServerConnection(clientConnection, DispatcherCloseReason.TRANSFER_FAILED);
                        });
    }
}
