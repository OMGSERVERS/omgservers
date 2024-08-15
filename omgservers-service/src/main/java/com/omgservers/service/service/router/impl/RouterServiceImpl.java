package com.omgservers.service.service.router.impl;

import com.omgservers.service.service.router.RouterService;
import com.omgservers.service.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.service.service.router.dto.CloseClientConnectionResponse;
import com.omgservers.service.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.service.service.router.dto.CloseServerConnectionResponse;
import com.omgservers.service.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.service.service.router.dto.RouteServerConnectionResponse;
import com.omgservers.service.service.router.dto.TransferClientBinaryMessageRequest;
import com.omgservers.service.service.router.dto.TransferClientBinaryMessageResponse;
import com.omgservers.service.service.router.dto.TransferClientTextMessageRequest;
import com.omgservers.service.service.router.dto.TransferClientTextMessageResponse;
import com.omgservers.service.service.router.dto.TransferServerBinaryMessageRequest;
import com.omgservers.service.service.router.dto.TransferServerBinaryMessageResponse;
import com.omgservers.service.service.router.dto.TransferServerTextMessageRequest;
import com.omgservers.service.service.router.dto.TransferServerTextMessageResponse;
import com.omgservers.service.service.router.impl.method.closeClientConnection.CloseClientConnectionMethod;
import com.omgservers.service.service.router.impl.method.closeServerConnection.CloseServerConnectionMethod;
import com.omgservers.service.service.router.impl.method.routeServerConnection.RouteServerConnectionMethod;
import com.omgservers.service.service.router.impl.method.transferClientBinaryMessage.TransferClientBinaryMessageMethod;
import com.omgservers.service.service.router.impl.method.transferClientTextMessage.TransferClientTextMessageMethod;
import com.omgservers.service.service.router.impl.method.transferServerBinaryMessage.TransferServerBinaryMessageMethod;
import com.omgservers.service.service.router.impl.method.transferServerTextMessage.TransferServerTextMessageMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RouterServiceImpl implements RouterService {

    final TransferServerBinaryMessageMethod transferServerBinaryMessageMethod;
    final TransferClientBinaryMessageMethod transferClientBinaryMessageMethod;
    final TransferServerTextMessageMethod transferServerTextMessageMethod;
    final TransferClientTextMessageMethod transferClientTextMessageMethod;
    final RouteServerConnectionMethod routeServerConnectionMethod;
    final CloseClientConnectionMethod closeClientConnectionMethod;
    final CloseServerConnectionMethod closeServerConnectionMethod;

    @Override
    public Uni<RouteServerConnectionResponse> routeServerConnection(@Valid final RouteServerConnectionRequest request) {
        return routeServerConnectionMethod.routeServerConnection(request);
    }

    @Override
    public Uni<CloseClientConnectionResponse> closeClientConnection(@Valid final CloseClientConnectionRequest request) {
        return closeClientConnectionMethod.closeClientConnection(request);
    }

    @Override
    public Uni<CloseServerConnectionResponse> closeServerConnection(@Valid final CloseServerConnectionRequest request) {
        return closeServerConnectionMethod.closeServerConnection(request);
    }

    @Override
    public Uni<TransferServerTextMessageResponse> transferServerTextMessage(
            @Valid final TransferServerTextMessageRequest request) {
        return transferServerTextMessageMethod.transferServerTextMessage(request);
    }

    @Override
    public Uni<TransferClientTextMessageResponse> transferClientTextMessage(
            @Valid final TransferClientTextMessageRequest request) {
        return transferClientTextMessageMethod.transferClientTextMessage(request);
    }

    @Override
    public Uni<TransferServerBinaryMessageResponse> transferServerBinaryMessage(
            @Valid final TransferServerBinaryMessageRequest request) {
        return transferServerBinaryMessageMethod.transferServerBinaryMessage(request);
    }

    @Override
    public Uni<TransferClientBinaryMessageResponse> transferClientBinaryMessage(
            @Valid final TransferClientBinaryMessageRequest request) {
        return transferClientBinaryMessageMethod.transferClientBinaryMessage(request);
    }
}
